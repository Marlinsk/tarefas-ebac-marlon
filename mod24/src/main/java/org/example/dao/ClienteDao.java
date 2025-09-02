package org.example.dao;

import org.example.domain.Cliente;

public class ClienteDao implements IClienteDao {

    public void salvar(Cliente cliente) {
        throw new UnsupportedOperationException("Não funciona sem config de banco");
    }

    @Override
    public Cliente buscar(String cpf) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }

    @Override
    public void atualizar(Cliente cliente) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }

    @Override
    public void excluir(String cpf) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }
}
