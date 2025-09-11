package dev.lucaslowhan.prodmanager.controller.categoria;

import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaRequestDTO;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaResponseDTO;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaUpdateDTO;
import dev.lucaslowhan.prodmanager.service.categoria.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.Entity;
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
@RequestMapping("/categoria")
@SecurityRequirement(name="bearer-key")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaResponseDTO> cadastrar(@RequestBody @Valid CategoriaRequestDTO dto, UriComponentsBuilder uriBuilder){
        var detalhesCategoria = service.cadastrar(dto);
        var uri = uriBuilder.path("/categoria/{id}").buildAndExpand(detalhesCategoria.id()).toUri();
        return ResponseEntity.created(uri).body(detalhesCategoria);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> listar(@PageableDefault(size = 10, sort = {"id"})Pageable pageable){
        Page<CategoriaResponseDTO> categorias = service.listar(pageable);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> detalhar(@PathVariable Long id){
        CategoriaResponseDTO dto = service.detalhar(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<CategoriaResponseDTO> atualizar(@RequestBody @Valid CategoriaUpdateDTO dto){
        CategoriaResponseDTO dto1 = service.atualizar(dto);
        return ResponseEntity.ok(dto1);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
