package dev.lucaslowhan.prodmanager.repository.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Object findByProduto(Produto produto);

    Page<Movimentacao> findByProdutoId(Long produtoId, Pageable pageable);

}
