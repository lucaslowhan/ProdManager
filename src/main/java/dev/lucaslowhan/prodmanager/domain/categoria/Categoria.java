package dev.lucaslowhan.prodmanager.domain.categoria;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaRequestDTO;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaUpdateDTO;
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

    public Categoria(CategoriaRequestDTO dto){
        this.ativa = true;
        this.nome = dto.nome();
        if(dto.descricao()!=null) {
            this.descricao = dto.descricao();
        }
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizar(CategoriaUpdateDTO dto){
        if(dto.nome() != null  && !dto.nome().isBlank()){
            this.nome = dto.nome();
        }
        if(dto.descricao() != null && !dto.descricao().isBlank()){
            this.descricao = dto.descricao();
        }
    }

    public void desativar() {
        this.ativa = false;
    }
}
