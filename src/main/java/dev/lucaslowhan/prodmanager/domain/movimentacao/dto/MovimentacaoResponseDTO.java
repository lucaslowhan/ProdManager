package dev.lucaslowhan.prodmanager.domain.movimentacao.dto;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.movimentacao.TipoMovimentacao;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(
        Long id,
        ProdutoResponseDTO produtoResponseDTO,
        TipoMovimentacao tipoMovimentacao,
        Integer quantidade,
        Integer estoqueAnterior,
        Integer estoqueAtualizado,
        String descricao,
        LocalDateTime dataMovimentacao
) {
    public MovimentacaoResponseDTO(Movimentacao movimentacao){
        this(
                movimentacao.getId(),
                new ProdutoResponseDTO(movimentacao.getProduto()),
                movimentacao.getTipoMovimentacao(),
                movimentacao.getQuantidade(),
                movimentacao.getEstoqueAnterior(),
                movimentacao.getEstoqueAtualizado(),
                movimentacao.getDescricao(),
                movimentacao.getDataMovimentacao()
        );
    }
}
