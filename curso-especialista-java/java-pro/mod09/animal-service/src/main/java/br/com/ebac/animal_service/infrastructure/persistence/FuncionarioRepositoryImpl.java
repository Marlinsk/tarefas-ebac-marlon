package br.com.ebac.animal_service.infrastructure.persistence;

import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.repository.FuncionarioRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    private final FuncionarioJpaRepository jpaRepository;

    public FuncionarioRepositoryImpl(FuncionarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        return jpaRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Funcionario> findByCpf(String cpf) {
        return jpaRepository.findByCpf(cpf);
    }

    @Override
    public List<Funcionario> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
