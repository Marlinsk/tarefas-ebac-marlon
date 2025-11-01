package com.example.produto.core.repositories;

import com.example.produto.core.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsBySku(String sku);
    Optional<Produto> findBySku(String sku);
}
