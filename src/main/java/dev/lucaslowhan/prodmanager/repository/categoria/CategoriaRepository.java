package dev.lucaslowhan.prodmanager.repository.categoria;

import dev.lucaslowhan.prodmanager.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    Boolean existsByNome(String nome);
}
