package com.example.clientes.web;

import com.example.clientes.core.entity.Cliente;
import com.example.clientes.web.dto.ClienteRequest;
import com.example.clientes.web.dto.ClienteResponse;
import com.example.clientes.web.dto.ClienteUpdateRequest;

public class ClienteMapper {

    private ClienteMapper() {}

    public static Cliente toEntity(ClienteRequest request) {
        var c = new Cliente();
        c.setNome(request.nome());
        c.setEmail(request.email());
        c.setCpf(request.cpf());
        c.setTelefone(request.telefone());
        return c;
    }

    public static void updateEntity(Cliente c, ClienteUpdateRequest request) {
        if (request.nome() != null) c.setNome(request.nome());
        if (request.email() != null) c.setEmail(request.email());
        if (request.cpf() != null) c.setCpf(request.cpf());
        if (request.telefone() != null) c.setTelefone(request.telefone());
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone(), cliente.getCriadoEm(), cliente.getAtualizadoEm());
    }
}
