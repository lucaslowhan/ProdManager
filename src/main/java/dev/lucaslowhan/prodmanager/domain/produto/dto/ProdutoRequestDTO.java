package dev.lucaslowhan.prodmanager.domain.produto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório!")
        String nome,
        String descricao,

        @NotNull(message = "O preço do produto é obrigatório!")
        @DecimalMin(value = "0.0", inclusive = false, message = "O Preço deve ser maior que zero!")
        BigDecimal preco,

        @NotNull(message = "A quantidade em estoque é obrigatória!")
        @Min(value = 0,message = "A quantidade em estoque não pode ser negativa!")
        Integer quantidadeEstoque,

        @NotNull(message = "O estoque mínimo é obrigatório!")
        @Min(value = 0,message = "O estoque mínimo não pode ser negativo!")
        Integer estoqueMinimo,

        @NotBlank(message = "O SKU do produto é obrigatório!")
        String sku,

        @NotNull(message = "A categoria é obrigatória!")
        Long categoriaId
) {
}
