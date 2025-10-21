package com.example.project.app.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.example.project.app.dao.IClienteDAO;
import com.example.project.app.domain.Cliente;
import com.example.project.app.exceptions.DAOException;
import com.example.project.app.exceptions.MaisDeUmRegistroException;
import com.example.project.app.exceptions.TableException;
import com.example.project.app.services.generic.GenericService;

@Stateless
public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
	
	private IClienteDAO clienteDAO;
	
	@Inject
	public ClienteService(IClienteDAO clienteDAO) {
		super(clienteDAO);
		this.clienteDAO = clienteDAO;
	}

	@Override
	public Cliente buscarPorCPF(Long cpf) throws DAOException {
		try {
			return this.dao.consultar(cpf);
		} catch (MaisDeUmRegistroException | TableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cliente> filtrarClientes(String query) {
		return clienteDAO.filtrarClientes(query);
	}

}
