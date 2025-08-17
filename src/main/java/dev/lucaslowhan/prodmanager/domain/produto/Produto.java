package dev.lucaslowhan.prodmanager.domain.produto;

import dev.lucaslowhan.prodmanager.domain.categoria.Categoria;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Produto")
@Table(name = "produtos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "sku")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEstoque;
    @Column(nullable = false)
    private Integer estoqueMinimo;

    @Column(nullable = false, unique = true)
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id",nullable = false)
    private Categoria categoria;

    private LocalDateTime dataCriacao;
    private Boolean ativo;

    public Produto(ProdutoRequestDTO produtoRequestDTO, Categoria categoria){
        this.ativo = true;
        this.nome = produtoRequestDTO.nome();
        this.descricao = produtoRequestDTO.descricao();
        this.preco = produtoRequestDTO.preco();
        this.quantidadeEstoque = produtoRequestDTO.quantidadeEstoque();
        this.estoqueMinimo = produtoRequestDTO.estoqueMinimo();
        this.sku = produtoRequestDTO.sku();
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
    }

}
