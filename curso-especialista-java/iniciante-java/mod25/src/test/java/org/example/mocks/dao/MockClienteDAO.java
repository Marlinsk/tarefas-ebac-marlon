package org.example.mocks.dao;

import org.example.application.dao.interfaces.IClienteDAO;
import org.example.application.domain.Cliente;

import java.util.List;
import java.util.stream.Collectors;

public class MockClienteDAO extends BaseMockDAO<Cliente, Long> implements IClienteDAO {

    @Override
    protected Long getKey(Cliente c) {
        return c.getCpf();
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        if (email == null) return null;
        return store.values().stream().filter(c -> c.getEmail() != null && c.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        if (nome == null) return List.of();
        String n = nome.toLowerCase();
        return store.values().stream().filter(c -> c.getNome() != null && c.getNome().toLowerCase().contains(n)).collect(Collectors.toList());
    }

    @Override
    public boolean existePorTelefone(String telefone) {
        if (telefone == null) return false;
        return store.values().stream().anyMatch(c -> telefone.equals(c.getTelefone()));
    }

    @Override
    public Cliente buscarPorCpf(Long cpf) {
        return consultar(cpf);
    }
}
