package org.example.application.services.interfaces;

import org.example.application.domain.Cliente;
import org.example.application.services.generic.IGenericService;

import java.util.List;

public interface IClienteService extends IGenericService<Cliente, Long> {
    Cliente buscarPorEmail(String email);
    List<Cliente> buscarPorNome(String nome);
    boolean existePorTelefone(String telefone);
    Cliente buscarPorCpf(Long cpf);
}
