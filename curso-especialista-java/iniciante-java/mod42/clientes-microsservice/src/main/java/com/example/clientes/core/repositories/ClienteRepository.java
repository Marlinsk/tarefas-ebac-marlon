package com.example.clientes.core.repositories;

import com.example.clientes.core.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
}
