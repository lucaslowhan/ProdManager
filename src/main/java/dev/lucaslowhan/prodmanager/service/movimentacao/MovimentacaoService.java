package dev.lucaslowhan.prodmanager.service.movimentacao;

import dev.lucaslowhan.prodmanager.domain.audit.TipoOperacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.TipoMovimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.infra.exception.BusinessException;
import dev.lucaslowhan.prodmanager.infra.exception.ResourceNotFoundException;
import dev.lucaslowhan.prodmanager.repository.movimentacao.MovimentacaoRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import dev.lucaslowhan.prodmanager.service.audit.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public MovimentacaoResponseDTO registrarMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        Produto produto = produtoRepository.findById(movimentacaoRequestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Integer estoqueAnterior = produto.getQuantidadeEstoque();

        Integer estoqueAtualizado;
        TipoOperacao tipoOperacao = movimentacaoRequestDTO.tipoMovimentacao() == TipoMovimentacao.ENTRADA ? TipoOperacao.MOV_ENTRADA : TipoOperacao.MOV_SAIDA;

        try {
            if (movimentacaoRequestDTO.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
                estoqueAtualizado = estoqueAnterior + movimentacaoRequestDTO.quantidade();
            } else {
                if (estoqueAnterior < movimentacaoRequestDTO.quantidade()) {
                    throw new BusinessException("Estoque insuficiente para saída");
                }
                estoqueAtualizado = estoqueAnterior - movimentacaoRequestDTO.quantidade();
            }

            produtoRepository.atualizarEstoque(produto.getId(), estoqueAtualizado);

            Movimentacao mov = new Movimentacao(movimentacaoRequestDTO, produto, estoqueAnterior, estoqueAtualizado);
            mov = movimentacaoRepository.save(mov);

            auditService.log(tipoOperacao, "Produto", produto.getId(),
                    Map.of("quantidadeEstoque", estoqueAnterior),
                    Map.of("quantidadeEstoque", estoqueAtualizado, "movimentacaoId", mov.getId()),
                    true, null);
            return new MovimentacaoResponseDTO(mov);
        } catch (RuntimeException ex) {
            auditService.log(tipoOperacao, "Produto", produto.getId(),
                    Map.of("quantidadeEstoque", estoqueAnterior),
                    null, false, ex.getMessage());
            throw ex;
        }
    }




    public Page<MovimentacaoResponseDTO> listar(Pageable pageable) {
        return movimentacaoRepository.findAll(pageable)
                .map(MovimentacaoResponseDTO::new);
    }

    public MovimentacaoResponseDTO buscarPorId(Long id) {
        var movimentacao = movimentacaoRepository.getReferenceById(id);
        return new MovimentacaoResponseDTO(movimentacao);
    }

    public Page<MovimentacaoResponseDTO> listarPorProduto(Long produtoId, Pageable pageable) {
        return movimentacaoRepository.findByProdutoId(produtoId,pageable)
                .map(MovimentacaoResponseDTO::new);
    }
}
