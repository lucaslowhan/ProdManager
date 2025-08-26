package dev.lucaslowhan.prodmanager.service.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.TipoMovimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.infra.exception.BusinessException;
import dev.lucaslowhan.prodmanager.infra.exception.ConflictException;
import dev.lucaslowhan.prodmanager.infra.exception.ResourceNotFoundException;
import dev.lucaslowhan.prodmanager.repository.movimentacao.MovimentacaoRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public MovimentacaoResponseDTO registrarMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO){
        Produto produto = produtoRepository.findById(movimentacaoRequestDTO.produtoId())
                .orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        Integer estoqueAnterior = produto.getQuantidadeEstoque();

        Integer estoqueAtualizado;
        if (movimentacaoRequestDTO.quantidade()>0) {
            if (movimentacaoRequestDTO.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
                estoqueAtualizado = estoqueAnterior + movimentacaoRequestDTO.quantidade();
            }else{
                if(estoqueAnterior < movimentacaoRequestDTO.quantidade()){
                    throw new BusinessException("Estoque insuficiente para saída");
                }
                estoqueAtualizado = estoqueAnterior - movimentacaoRequestDTO.quantidade();
            }

            produtoRepository.atualizarEstoque(produto.getId(), estoqueAtualizado);
        }else {
            throw new BusinessException("Quantidade da movimentação não pode ser negativa");
        }

        Movimentacao movimentacao = new Movimentacao(
                movimentacaoRequestDTO,
                produto,
                estoqueAnterior,
                estoqueAtualizado
        );

        movimentacaoRepository.save(movimentacao);

        return new MovimentacaoResponseDTO(movimentacao);

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
