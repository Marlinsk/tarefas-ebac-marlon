package com.example.project.app.service;

import java.util.List;

import com.example.project.app.domain.Cliente;
import com.example.project.app.exceptions.DAOException;
import com.example.project.app.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {

	Cliente buscarPorCPF(Long cpf) throws DAOException;

	List<Cliente> filtrarClientes(String query);

}
