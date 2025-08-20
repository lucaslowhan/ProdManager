package dev.lucaslowhan.prodmanager.service.relatorio;

import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioProdutoMovimentadoDTO;
import dev.lucaslowhan.prodmanager.repository.movimentacao.MovimentacaoRepository;
import dev.lucaslowhan.prodmanager.service.movimentacao.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<RelatorioProdutoMovimentadoDTO> produtosMaisMovimentados(){
        return movimentacaoRepository.listarProdutosMaisMovimentados();
    }
}
