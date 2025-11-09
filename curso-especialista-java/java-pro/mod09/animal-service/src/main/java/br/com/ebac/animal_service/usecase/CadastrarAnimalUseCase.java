package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastrarAnimalUseCase {

    private final AnimalRepository animalRepository;

    public CadastrarAnimalUseCase(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal execute(Animal animal) {
        return animalRepository.save(animal);
    }
}
