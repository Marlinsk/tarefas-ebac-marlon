package com.example.project.app.dao;

import java.util.List;

import com.example.project.app.dao.generic.IGenericDAO;
import com.example.project.app.domain.Produto;

public interface IProdutoDAO extends IGenericDAO<Produto, String>{

	List<Produto> filtrarProdutos(String query);

}
