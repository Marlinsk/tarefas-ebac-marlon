package com.example.clientes.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequest(
        @Size(max = 120) String nome,
        @Email @Size(max = 150) String email,
        @Size(max = 14) String cpf,
        @Size(max = 20) String telefone
) {}
