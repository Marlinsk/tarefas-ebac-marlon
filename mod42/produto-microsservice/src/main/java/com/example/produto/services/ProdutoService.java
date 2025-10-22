package com.example.produto.services;

import com.example.produto.core.entity.Produto;
import com.example.produto.core.repositories.ProdutoRepository;
import com.example.produto.web.ProdutoMapper;
import com.example.produto.web.dto.ProdutoRequest;
import com.example.produto.web.dto.ProdutoResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {
        if (repository.existsBySku(request.sku())) {
            throw new DataIntegrityViolationException("SKU já existente: " + request.sku());
        }
        Produto salvo = repository.save(ProdutoMapper.toEntity(request));
        return ProdutoMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listar(@PageableDefault(page = 0, size = 10, sort = "sku", direction = Sort.Direction.ASC) Pageable pageable)  {
        return repository.findAll(pageable).map(ProdutoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse obter(Long id) {
        var produto = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest req) {
        var produto = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        ProdutoMapper.updateEntity(produto, req);
        return ProdutoMapper.toResponse(repository.save(produto));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
