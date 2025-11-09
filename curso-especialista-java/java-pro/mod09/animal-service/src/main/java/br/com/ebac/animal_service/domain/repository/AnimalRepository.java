package br.com.ebac.animal_service.domain.repository;

import br.com.ebac.animal_service.domain.entity.Animal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Animal save(Animal animal);

    Optional<Animal> findById(Long id);

    List<Animal> findAll();

    List<Animal> findDisponiveis();

    List<Animal> findAdotados();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Animal> findByDataEntradaBetween(LocalDate dataInicio, LocalDate dataFim);
}
