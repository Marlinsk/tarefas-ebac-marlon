package com.example.project.app.dao;

import com.example.project.app.dao.generic.IGenericDAO;
import com.example.project.app.domain.Venda;
import com.example.project.app.exceptions.DAOException;
import com.example.project.app.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda, Long>{

	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
	
	public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

	public Venda consultarComCollection(Long id);

}
