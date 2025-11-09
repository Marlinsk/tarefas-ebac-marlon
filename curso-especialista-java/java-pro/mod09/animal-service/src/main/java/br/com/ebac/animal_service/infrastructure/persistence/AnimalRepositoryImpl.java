package br.com.ebac.animal_service.infrastructure.persistence;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimalRepositoryImpl implements AnimalRepository {

    private final AnimalJpaRepository jpaRepository;

    public AnimalRepositoryImpl(AnimalJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Animal save(Animal animal) {
        return jpaRepository.save(animal);
    }

    @Override
    public Optional<Animal> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Animal> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Animal> findDisponiveis() {
        return jpaRepository.findDisponiveis();
    }

    @Override
    public List<Animal> findAdotados() {
        return jpaRepository.findAdotados();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Animal> findByDataEntradaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return jpaRepository.findByDataEntradaBetween(dataInicio, dataFim);
    }
}
