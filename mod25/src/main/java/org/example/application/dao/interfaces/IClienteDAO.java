package org.example.application.dao.interfaces;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.domain.Cliente;

import java.util.List;

public interface IClienteDAO extends IGenericDAO<Cliente, Long> {
    Cliente buscarPorEmail(String email);
    List<Cliente> buscarPorNome(String nome);
    boolean existePorTelefone(String telefone);
    Cliente buscarPorCpf(Long cpf);
}
