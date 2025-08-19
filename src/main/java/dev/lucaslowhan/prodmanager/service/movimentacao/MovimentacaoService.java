package dev.lucaslowhan.prodmanager.service.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.TipoMovimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.repository.movimentacao.MovimentacaoRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        Integer estoqueAnterior = produto.getQuantidadeEstoque();

        Integer estoqueAtualizado;
        if (movimentacaoRequestDTO.quantidade()>0) {
            if (movimentacaoRequestDTO.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
                estoqueAtualizado = estoqueAnterior + movimentacaoRequestDTO.quantidade();
            }else{
                if(estoqueAnterior < movimentacaoRequestDTO.quantidade()){
                    throw new RuntimeException("Estoque insuficiente para saída");
                }
                estoqueAtualizado = estoqueAnterior - movimentacaoRequestDTO.quantidade();
            }

            produtoRepository.atualizarEstoque(produto.getId(), estoqueAtualizado);
        }else {
            throw new RuntimeException("Quantidade da movimentação não pode ser negativa");
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
}
