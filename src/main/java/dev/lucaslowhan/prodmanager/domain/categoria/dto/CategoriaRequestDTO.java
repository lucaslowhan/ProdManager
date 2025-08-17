package dev.lucaslowhan.prodmanager.domain.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "Campo obrigatorio!")
        String nome,
        String descricao
) {
}
