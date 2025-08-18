package main.java.com.projeto1.dao;

import main.java.com.projeto1.domain.Cliente;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;

import java.util.List;
import java.util.Optional;

public interface IClienteDao extends IGenericDao<Cliente, String> {
    Optional<ClientePessoaFisica> buscarPfPorCpf(String cpf);
    Optional<ClientePessoaJuridica> buscarPjPorCnpj(String cnpj);
    List<ClientePessoaFisica> listarPessoasFisicas();
    List<ClientePessoaJuridica> listarPessoasJuridicas();
}
