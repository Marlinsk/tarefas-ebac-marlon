package main.java.com.mir.domain;

import main.java.com.mir.annotations.Tabela;

@Tabela(nome = "Produtos")
public class Produto {
    private Long id;
    private String nome;
}
