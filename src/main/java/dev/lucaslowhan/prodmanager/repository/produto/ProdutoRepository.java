package dev.lucaslowhan.prodmanager.repository.produto;

import aj.org.objectweb.asm.commons.Remapper;
import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioSaldoTotalEstoqueDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.PropertyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    boolean existsBySku(String sku);

    Page<Produto> findByCategoriaId(Long categoriaId, Pageable pageable);

    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < p.estoqueMinimo AND p.ativo = true")
    List<Produto> findProdutosAbaixoDoEstoqueMinimo();

    @Modifying
    @Query("UPDATE Produto p SET p.quantidadeEstoque = :estoqueAtualizado WHERE p.id = :id")
    void atualizarEstoque(Long id, Integer estoqueAtualizado);

    @Query("""
        SELECT new dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioSaldoTotalEstoqueDTO(
            p.id,
            p.nome,
            p.quantidadeEstoque
        )
        FROM Produto p
""")
    List<RelatorioSaldoTotalEstoqueDTO> saldoTotalEstoque();
}
