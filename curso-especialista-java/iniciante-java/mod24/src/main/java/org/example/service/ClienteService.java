package org.example.service;

import org.example.dao.IClienteDao;
import org.example.domain.Cliente;

public class ClienteService implements IClienteService {

    private final IClienteDao clienteDao;

    public ClienteService(IClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public String salvar(Cliente cliente) {
        clienteDao.salvar(cliente);
        return "Cliente salvo com sucesso!";
    }

    @Override
    public Cliente buscar(String cpf) {
        return clienteDao.buscar(cpf);
    }

    @Override
    public String atualizar(Cliente cliente) {
        clienteDao.atualizar(cliente);
        return "Cliente atualizado com sucesso!";
    }

    @Override
    public String excluir(String cpf) {
        clienteDao.excluir(cpf);
        return "Cliente excluído com sucesso!";
    }
}
