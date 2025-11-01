package com.example.produto.web;

import com.example.produto.core.entity.Produto;
import com.example.produto.web.dto.ProdutoRequest;
import com.example.produto.web.dto.ProdutoResponse;

public final class ProdutoMapper {

    private ProdutoMapper() {}

    public static Produto toEntity(ProdutoRequest request) {
        var produto = new Produto();
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setSku(request.sku());
        return produto;
    }

    public static void updateEntity(Produto p, ProdutoRequest request) {
        if (request.nome() != null) p.setNome(request.nome());
        if (request.descricao() != null) p.setDescricao(request.descricao());
        if (request.preco() != null) p.setPreco(request.preco());
        if (request.sku() != null) p.setSku(request.sku());
    }

    public static ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getSku(), produto.getCriadoEm(), produto.getAtualizadoEm());
    }
}
