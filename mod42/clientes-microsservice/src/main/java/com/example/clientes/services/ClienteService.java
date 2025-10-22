package com.example.clientes.services;

import com.example.clientes.core.entity.Cliente;
import com.example.clientes.core.repositories.ClienteRepository;
import com.example.clientes.web.ClienteMapper;
import com.example.clientes.web.dto.ClienteRequest;
import com.example.clientes.web.dto.ClienteResponse;
import com.example.clientes.web.dto.ClienteUpdateRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {
        if (this.repository.existsByEmail(request.email())) {
            throw new DataIntegrityViolationException("E-mail já existente: " + request.email());
        }
        if (request.cpf() != null && !request.cpf().isBlank() && this.repository.existsByCpf(request.cpf())) {
            throw new DataIntegrityViolationException("CPF já existente: " + request.cpf());
        }
        Cliente salvo = this.repository.save(ClienteMapper.toEntity(request));
        return ClienteMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.repository.findAll(pageable).map(ClienteMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClienteResponse obter(Long id) {
        var c = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        return ClienteMapper.toResponse(c);
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteUpdateRequest request) {
        var cliente = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        if (request.email() != null && !request.email().equals(cliente.getEmail()) && this.repository.existsByEmail(request.email())) {
            throw new DataIntegrityViolationException("E-mail já existente: " + request.email());
        }
        if (request.cpf() != null && !request.cpf().equals(cliente.getCpf()) && this.repository.existsByCpf(request.cpf())) {
            throw new DataIntegrityViolationException("CPF já existente: " + request.cpf());
        }

        ClienteMapper.updateEntity(cliente, request);
        return ClienteMapper.toResponse(this.repository.save(cliente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!this.repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado: " + id);
        }
        this.repository.deleteById(id);
    }
}
