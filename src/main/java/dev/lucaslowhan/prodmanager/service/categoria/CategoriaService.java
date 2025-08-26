package dev.lucaslowhan.prodmanager.service.categoria;

import dev.lucaslowhan.prodmanager.domain.categoria.Categoria;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaRequestDTO;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaResponseDTO;
import dev.lucaslowhan.prodmanager.domain.categoria.dto.CategoriaUpdateDTO;
import dev.lucaslowhan.prodmanager.repository.categoria.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    public Object listar;
    @Autowired
    private CategoriaRepository repository;

    public CategoriaResponseDTO cadastrar(CategoriaRequestDTO dto){
        var jaCadastrado = repository.existsByNome(dto.nome());
        if(jaCadastrado){
            throw new RuntimeException("Categoria já cadastrada!");
        }
        var categoria = new Categoria(dto);
        repository.save(categoria);
        return new CategoriaResponseDTO(categoria);
    }

    public Page<CategoriaResponseDTO> listar(Pageable pageable){
        return repository.findAll(pageable)
                .map(CategoriaResponseDTO::new);
    }

    public CategoriaResponseDTO detalhar(Long id) {
        var categoria = repository.getReferenceById(id);
        if(categoria==null){
            throw new RuntimeException("Categoria incorreta!");
        }
        return new CategoriaResponseDTO(categoria);
    }

    public CategoriaResponseDTO atualizar(CategoriaUpdateDTO dto) {
        var categoria  = repository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada!"));

        categoria.atualizar(dto);
        repository.save(categoria);

        return new CategoriaResponseDTO(categoria);
    }

    public void excluirCategoria(Long id) {
        var categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoria.desativar();
        repository.save(categoria);
    }

    public Categoria buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
    }
}
