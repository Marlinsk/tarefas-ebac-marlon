package org.example.application.dao;

import org.example.application.dao.generic.GenericDAO;
import org.example.application.dao.interfaces.IClienteDAO;
import org.example.application.dao.mapper.SingletonMap;
import org.example.application.domain.Cliente;

import java.util.*;
import java.util.stream.Collectors;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

    public Cliente buscarPorEmail(String email) {
        if (email == null) return null;
        return buscarTodos().stream().filter(c -> c.getEmail() != null && c.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        if (nome == null) return List.of();
        return buscarTodos().stream().filter(c -> c.getNome() != null && c.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public boolean existePorTelefone(String telefone) {
        if (telefone == null) return false;
        return buscarTodos().stream().anyMatch(c -> c.getTelefone() != null && c.getTelefone().equals(telefone));
    }

    @Override
    public Cliente buscarPorCpf(Long cpf) {
        if (cpf == null) return null;
        return consultar(cpf);
    }

    public void limparStore() {
        Map<String, Cliente> store = SingletonMap.getInstance().getTypedStore(Cliente.class);
        store.clear();
    }

    public int contar() {
        Map<String, Cliente> store = SingletonMap.getInstance().getTypedStore(Cliente.class);
        return store.size();
    }

    public Set<String> chaves() {
        Map<String, Cliente> store = SingletonMap.getInstance().getTypedStore(Cliente.class);
        return new LinkedHashSet<>(store.keySet());
    }

    public List<Cliente> snapshot() {
        Map<String, Cliente> store = SingletonMap.getInstance().getTypedStore(Cliente.class);
        return new ArrayList<>(store.values());
    }
}
