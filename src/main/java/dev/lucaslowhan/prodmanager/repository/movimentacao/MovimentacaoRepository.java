package dev.lucaslowhan.prodmanager.repository.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
