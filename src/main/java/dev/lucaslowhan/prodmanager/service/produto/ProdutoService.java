package dev.lucaslowhan.prodmanager.service.produto;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoUpdateDTO;
import dev.lucaslowhan.prodmanager.repository.categoria.CategoriaRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import dev.lucaslowhan.prodmanager.service.categoria.CategoriaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto){
        if(produtoRepository.existsBySku(dto.sku())){
            throw new EntityExistsException("Já existe um produto cadastrado com o SKU: " + dto.sku());
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
        var produto = produtoRepository.getReferenceById(id);
        if(produto==null){
            throw new RuntimeException("Produto incorreto!");
        }
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(@Valid ProdutoUpdateDTO dto) {
        var produto = produtoRepository.findById(dto.id())
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado!"));

        produto.atualizar(dto);
        produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    public void excluirProduto(Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Produto não encontrado!"));

        produto.desativar();
        produtoRepository.save(produto);
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
