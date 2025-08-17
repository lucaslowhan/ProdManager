package dev.lucaslowhan.prodmanager.service.produto;

import dev.lucaslowhan.prodmanager.domain.produto.Produto;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoRequestDTO;
import dev.lucaslowhan.prodmanager.domain.produto.dto.ProdutoResponseDTO;
import dev.lucaslowhan.prodmanager.repository.categoria.CategoriaRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import dev.lucaslowhan.prodmanager.service.categoria.CategoriaService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto){
        if(produtoRepository.existsBySku(dto.sku())){
            throw new EntityExistsException("Já existe um produto cadastrado com o SKU: " + dto.sku());
        }

        var categoria = categoriaService.buscarPorId(dto.categoriaId());

        var produto = new Produto(dto,categoria);
        produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto);
    }
}
