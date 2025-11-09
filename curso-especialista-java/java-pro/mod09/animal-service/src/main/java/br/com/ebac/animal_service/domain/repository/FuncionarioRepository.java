package br.com.ebac.animal_service.domain.repository;

import br.com.ebac.animal_service.domain.entity.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository {

    Funcionario save(Funcionario funcionario);

    Optional<Funcionario> findById(Long id);

    Optional<Funcionario> findByCpf(String cpf);

    List<Funcionario> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
