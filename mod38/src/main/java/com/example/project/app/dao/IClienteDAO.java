package com.example.project.app.dao;

import java.util.List;

import com.example.project.app.dao.generic.IGenericDAO;
import com.example.project.app.domain.Cliente;

public interface IClienteDAO extends IGenericDAO<Cliente, Long>{

	List<Cliente> filtrarClientes(String query);

}
