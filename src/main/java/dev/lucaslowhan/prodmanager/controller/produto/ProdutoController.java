package dev.lucaslowhan.prodmanager.controller.produto;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoUpdateDTO;
import dev.lucaslowhan.prodmanager.service.produto.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody @Valid ProdutoRequestDTO dto, UriComponentsBuilder uriBuilder){
        var detalhesProduto = produtoService.criarProduto(dto);
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(detalhesProduto.id()).toUri();
        return ResponseEntity.created(uri).body(detalhesProduto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listar(@PageableDefault(size = 10, sort = {"id"})Pageable pageable){
        Page<ProdutoResponseDTO> produtos = produtoService.listar(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> detalhar(@PathVariable Long id){
        ProdutoResponseDTO dto = produtoService.detalhar(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ProdutoResponseDTO> atualizar(@RequestBody @Valid ProdutoUpdateDTO dto){
        ProdutoResponseDTO dto1 = produtoService.atualizar(dto);
        return ResponseEntity.ok(dto1);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

}
