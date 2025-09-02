package org.example.service;

import org.example.domain.Cliente;

public interface IClienteService {

    String salvar(Cliente cliente);

    Cliente buscar(String cpf);

    String atualizar(Cliente cliente);

    String excluir(String cpf);
}
