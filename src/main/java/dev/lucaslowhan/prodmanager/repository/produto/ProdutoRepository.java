package dev.lucaslowhan.prodmanager.repository.produto;

import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    boolean existsBySku(String sku);
}
