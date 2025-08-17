package dev.lucaslowhan.prodmanager.domain.categoria.dto;


import dev.lucaslowhan.prodmanager.domain.categoria.Categoria;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean ativa,
        LocalDateTime dataCriacao){

    public CategoriaResponseDTO(Categoria categoria){
        this(categoria.getId(), categoria.getNome(), categoria.getDescricao(), categoria.getAtiva(),categoria.getDataCriacao());
    }
}
