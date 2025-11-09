package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarAnimaisAdotadosUseCase {

    private final AnimalRepository animalRepository;

    public ListarAnimaisAdotadosUseCase(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> execute() {
        return animalRepository.findAdotados();
    }
}
