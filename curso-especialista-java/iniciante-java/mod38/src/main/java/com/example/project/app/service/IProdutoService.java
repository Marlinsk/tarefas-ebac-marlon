package com.example.project.app.service;

import java.util.List;

import com.example.project.app.domain.Produto;
import com.example.project.app.services.generic.IGenericService;

public interface IProdutoService extends IGenericService<Produto, String> {

	List<Produto> filtrarProdutos(String query);

}
