package dev.lucaslowhan.prodmanager.domain.produto.dto;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO (
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque,
        Integer estoqueMinimo,
        String sku,
        CategoriaResponseDTO categoria,
        Boolean ativo,
        LocalDateTime dataCriacao
){
    public ProdutoResponseDTO(Produto produto){
        this(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getPreco(),produto.getQuantidadeEstoque(), produto.getEstoqueMinimo(),
                produto.getSku(),new CategoriaResponseDTO(produto.getCategoria()),produto.getAtivo(),produto.getDataCriacao());
    }
}
