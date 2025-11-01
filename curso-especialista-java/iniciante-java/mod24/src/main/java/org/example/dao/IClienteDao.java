package org.example.dao;

import org.example.domain.Cliente;

public interface IClienteDao {

    void salvar(Cliente cliente);

    Cliente buscar(String cpf);

    void atualizar(Cliente cliente);

    void excluir(String cpf);
}
