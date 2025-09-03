package org.example.application.services;

import org.example.application.dao.interfaces.IClienteDAO;
import org.example.application.domain.Cliente;
import org.example.application.services.generic.GenericService;
import org.example.application.services.interfaces.IClienteService;

import java.util.List;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        super(clienteDAO);
        this.clienteDAO = clienteDAO;
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return clienteDAO.buscarPorEmail(email);
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        return clienteDAO.buscarPorNome(nome);
    }

    @Override
    public boolean existePorTelefone(String telefone) {
        return clienteDAO.existePorTelefone(telefone);
    }

    @Override
    public Cliente buscarPorCpf(Long cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }
}
