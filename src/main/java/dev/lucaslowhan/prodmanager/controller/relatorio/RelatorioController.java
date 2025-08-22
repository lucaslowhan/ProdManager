package dev.lucaslowhan.prodmanager.controller.relatorio;

import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioProdutoMovimentadoDTO;
import dev.lucaslowhan.prodmanager.service.relatorio.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/produtos-mais-movimentados")
    public ResponseEntity<List<RelatorioProdutoMovimentadoDTO>> listarProdutosMaisMovimentados(){
        return ResponseEntity.ok(relatorioService.produtosMaisMovimentados());
    }

    @GetMapping("/produtos-mais-movimentados/gerar")
    public ResponseEntity<byte[]>gerarRelatorio(@RequestParam(defaultValue = "pdf")String tipo){
        return relatorioService.gerarRelatorios(tipo);
    }
}
