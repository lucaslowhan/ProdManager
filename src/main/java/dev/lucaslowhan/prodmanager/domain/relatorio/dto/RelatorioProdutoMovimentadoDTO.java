package dev.lucaslowhan.prodmanager.domain.relatorio.dto;

import java.time.LocalDateTime;

public record RelatorioProdutoMovimentadoDTO(
        Long produtoId,
        String nomeProduto,
        Long totalMovimentacoes,
        Long quantidadeTotalMovimentada,
        LocalDateTime ultimaMovimentacao
) {
}
