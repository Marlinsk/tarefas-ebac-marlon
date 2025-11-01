package org.example.mocks;

import org.example.dao.IClienteDao;
import org.example.domain.Cliente;

import java.util.HashMap;
import java.util.Map;

public class ClienteDaoMock implements IClienteDao {

    private final Map<String, Cliente> db = new HashMap<>();

    @Override
    public void salvar(Cliente cliente) {
        validarCliente(cliente);
        String cpf = cliente.getCpf();
        if (db.containsKey(cpf)) {
            throw new IllegalArgumentException("Já existe cliente com CPF " + cpf);
        }
        db.put(cpf, cliente);
    }

    @Override
    public Cliente buscar(String cpf) {
        validarCpf(cpf);
        return db.get(cpf);
    }

    @Override
    public void atualizar(Cliente cliente) {
        validarCliente(cliente);
        String cpf = cliente.getCpf();
        if (!db.containsKey(cpf)) {
            throw new IllegalArgumentException("Não existe cliente com CPF " + cpf + " para atualizar.");
        }
        db.put(cpf, cliente);
    }

    @Override
    public void excluir(String cpf) {
        validarCpf(cpf);
        if (db.remove(cpf) == null) {
            throw new IllegalArgumentException("Não existe cliente com CPF " + cpf + " para excluir.");
        }
    }

    private void validarCliente(Cliente c) {
        if (c == null) throw new IllegalArgumentException("Cliente não pode ser nulo.");
        if (isBlank(c.getCpf())) throw new IllegalArgumentException("CPF é obrigatório.");
        if (isBlank(c.getNome())) throw new IllegalArgumentException("Nome é obrigatório.");
        if (isBlank(c.getEmail())) throw new IllegalArgumentException("Email é obrigatório.");
    }

    private void validarCpf(String cpf) {
        if (isBlank(cpf)) throw new IllegalArgumentException("CPF é obrigatório.");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
