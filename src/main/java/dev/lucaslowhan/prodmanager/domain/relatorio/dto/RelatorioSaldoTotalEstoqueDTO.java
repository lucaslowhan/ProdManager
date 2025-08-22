package dev.lucaslowhan.prodmanager.domain.relatorio.dto;

public record RelatorioSaldoTotalEstoqueDTO(
        Long produtoId,
        String nomeProduto,
        Integer saldoTotalEstoque
) {
}
