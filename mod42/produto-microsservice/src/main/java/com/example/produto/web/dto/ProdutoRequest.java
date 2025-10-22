package com.example.produto.web.dto;

import java.math.BigDecimal;

public record ProdutoRequest(String nome, String descricao, BigDecimal preco, String sku) {}
