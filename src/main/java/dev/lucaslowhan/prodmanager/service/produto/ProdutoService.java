package dev.lucaslowhan.prodmanager.service.produto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lucaslowhan.prodmanager.domain.audit.TipoOperacao;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoUpdateDTO;
import dev.lucaslowhan.prodmanager.infra.exception.BusinessException;
import dev.lucaslowhan.prodmanager.infra.exception.ConflictException;
import dev.lucaslowhan.prodmanager.infra.exception.ResourceNotFoundException;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import dev.lucaslowhan.prodmanager.service.audit.AuditService;
import dev.lucaslowhan.prodmanager.service.categoria.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuditService auditService;

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto){
        if(produtoRepository.existsBySku(dto.sku())){
            throw new ConflictException("Já existe um produto cadastrado com o SKU: " + dto.sku());
        }

        var categoria = categoriaService.buscarPorId(dto.categoriaId());

        var produto = new Produto(dto,categoria);
        produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto);
    }

    public Page<ProdutoResponseDTO> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(ProdutoResponseDTO::new);
    }

    public ProdutoResponseDTO detalhar(Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(@Valid ProdutoUpdateDTO dto) {
        var produto = produtoRepository.findById(dto.id())
                .orElseThrow(()-> new ResourceNotFoundException("Produto com ID " + dto.id() + " não encontrado"));

        produto.atualizar(dto);
        produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    public void excluirProduto(Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Produto com ID " + id + " não encontrado"));

        try {
            String before = objectMapper.writeValueAsString(produto);
            produto.desativar();
            produtoRepository.save(produto);

            String after = objectMapper.writeValueAsString(produto);

            auditService.log(TipoOperacao.EXCLUIR_PRODUTO,
                    "Produto",
                    produto.getId(),
                    before,
                    after,
                    true,
                    null);
        }catch (Exception ex){
            auditService.log(
                    TipoOperacao.EXCLUIR_PRODUTO,
                    "Produto",
                    produto.getId(),
                    produto,
                    null,
                    false,
                    ex.getMessage()
            );
            try {
                throw ex;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public Page<ProdutoResponseDTO> listarPorCategoria(Long categoriaId, Pageable pageable) {
        return produtoRepository.findByCategoriaId(categoriaId,pageable)
                .map(ProdutoResponseDTO::new);
    }

    public Page<ProdutoResponseDTO> listarPorNome(String nome, Pageable pageable) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ProdutoResponseDTO::new);
    }

    public List<ProdutoResponseDTO> listarAbaixoDoMinimo() {
        return produtoRepository.findProdutosAbaixoDoEstoqueMinimo()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }
}
