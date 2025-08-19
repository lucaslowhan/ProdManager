package dev.lucaslowhan.prodmanager.domain.movimentacao.dto;

import dev.lucaslowhan.prodmanager.domain.movimentacao.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoRequestDTO(
        @NotNull(message = "Id do produto obrigatorio")
        Long produtoId,
        @NotNull(message = "Tipo de movimentacao obrigatorio")
        TipoMovimentacao tipoMovimentacao,
        @NotNull(message = "Quantidade obrigatorio")
        Integer quantidade,
        @NotNull(message = "Descrição da movimentação obrigatorio")
        String descricao
) {
}
