package dev.lucaslowhan.prodmanager.domain.produto.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoUpdateDTO(
        @NotNull
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque,
        Integer estoqueMinimo
) {
}
