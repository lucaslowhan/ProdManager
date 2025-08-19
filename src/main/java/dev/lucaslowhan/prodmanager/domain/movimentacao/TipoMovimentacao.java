package dev.lucaslowhan.prodmanager.domain.movimentacao;

import lombok.Getter;

@Getter
public enum TipoMovimentacao {
    ENTRADA("Entrada de estoque"),
    SAIDA("Saída de estoque"),
    AJUSTE("Ajuste de estoque");

    private final String descricao;

    TipoMovimentacao(String descricao){
        this.descricao = descricao;
    }
}
