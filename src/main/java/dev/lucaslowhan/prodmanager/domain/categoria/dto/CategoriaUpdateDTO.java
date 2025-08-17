package dev.lucaslowhan.prodmanager.domain.categoria.dto;

import jakarta.validation.constraints.NotNull;

public record CategoriaUpdateDTO(
        @NotNull
        Long id,
        String nome,
        String descricao
) {
}
