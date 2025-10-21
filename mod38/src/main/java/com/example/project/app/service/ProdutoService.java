package com.example.project.app.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.example.project.app.dao.IProdutoDAO;
import com.example.project.app.domain.Produto;
import com.example.project.app.services.generic.GenericService;

@Stateless
public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {
	
	private IProdutoDAO produtoDao;

	@Inject
	public ProdutoService(IProdutoDAO produtoDao) {
		super(produtoDao);
		this.produtoDao = produtoDao;
	}

	@Override
	public List<Produto> filtrarProdutos(String query) {
		return produtoDao.filtrarProdutos(query);
	}

}
