package dev.lucaslowhan.prodmanager.controller.movimentacao;

import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.movimentacao.dto.MovimentacaoResponseDTO;
import dev.lucaslowhan.prodmanager.service.movimentacao.MovimentacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
@SecurityRequirement(name = "bearer-key")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> registrar(@RequestBody @Valid MovimentacaoRequestDTO movimentacaoRequestDTO){
        MovimentacaoResponseDTO responseDTO = movimentacaoService.registrarMovimentacao(movimentacaoRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MovimentacaoResponseDTO>> listar(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<MovimentacaoResponseDTO> movimentacoes = movimentacaoService.listar(pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(movimentacaoService.buscarPorId(id));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<Page<MovimentacaoResponseDTO>> listarPorProduto(@PathVariable Long produtoId, Pageable pageable){
        return ResponseEntity.ok(movimentacaoService.listarPorProduto(produtoId,pageable));
    }
}
