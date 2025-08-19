package dev.lucaslowhan.prodmanager.domain.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Movimentacao")
@Table(name = "movimentacoes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id" , nullable = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipoMovimentacao;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private Integer estoqueAnterior;

    @Column(nullable = false)
    private Integer estoqueAtualizado;

    @Column(nullable = false,updatable = false)
    private LocalDateTime dataMovimentacao;

    public Movimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO, Produto produto, Integer estoqueAnterior, Integer estoqueAtualizado){
        this.produto = produto;
        this.tipoMovimentacao = movimentacaoRequestDTO.tipoMovimentacao();
        this.quantidade = movimentacaoRequestDTO.quantidade();
        this.descricao = movimentacaoRequestDTO.descricao();;
        this.estoqueAnterior = estoqueAnterior;
        this.estoqueAtualizado = estoqueAtualizado;
        this.dataMovimentacao = LocalDateTime.now();
    }
}
