package dev.lucaslowhan.prodmanager.domain.categoria;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CadastrarCategoriaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Categoria")
@Table(name = "categorias")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean ativa;

    public Categoria(CadastrarCategoriaDTO dto){
        this.ativa = true;
        this.nome = dto.nome();
        if(dto.descricao()!=null) {
            this.descricao = dto.descricao();
        }
        this.dataCriacao = LocalDateTime.now();
    }
}
