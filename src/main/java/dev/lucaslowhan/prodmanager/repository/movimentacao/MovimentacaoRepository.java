package dev.lucaslowhan.prodmanager.repository.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.Movimentacao;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioProdutoMovimentadoDTO;
import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioSaldoTotalEstoqueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Object findByProduto(Produto produto);

    Page<Movimentacao> findByProdutoId(Long produtoId, Pageable pageable);


    @Query("""
        SELECT new dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioProdutoMovimentadoDTO(
            m.produto.id,
            m.produto.nome,
            COUNT(m),
            SUM(m.quantidade),
            MAX(m.dataMovimentacao)
        )
        FROM Movimentacao m
        GROUP BY m.produto.id, m.produto.nome
        ORDER BY COUNT(m) DESC
""")
    List<RelatorioProdutoMovimentadoDTO> listarProdutosMaisMovimentados();

}