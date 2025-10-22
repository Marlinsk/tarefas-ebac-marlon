package com.example.produto.web.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco, String sku, Instant criadoEm, Instant atualizadoEm) {}
