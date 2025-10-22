package com.example.clientes.web.dto;

import java.time.Instant;

public record ClienteResponse(Long id, String nome, String email, String cpf, String telefone, Instant criadoEm, Instant atualizadoEm) {}
