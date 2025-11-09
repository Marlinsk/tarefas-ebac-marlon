package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import br.com.ebac.animal_service.usecase.exception.AnimalNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EditarAnimalUseCase {

    private final AnimalRepository animalRepository;

    public EditarAnimalUseCase(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal execute(Long id, Animal animalAtualizado) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal com ID " + id + " não encontrado"));

        if (animalAtualizado.getNomeProvisorio() != null) {
            animal.setNomeProvisorio(animalAtualizado.getNomeProvisorio());
        }
        if (animalAtualizado.getIdadeEstimada() != null) {
            animal.setIdadeEstimada(animalAtualizado.getIdadeEstimada());
        }
        if (animalAtualizado.getRacaOuEspecie() != null) {
            animal.setRacaOuEspecie(animalAtualizado.getRacaOuEspecie());
        }
        if (animalAtualizado.getDescricaoCondicao() != null) {
            animal.setDescricaoCondicao(animalAtualizado.getDescricaoCondicao());
        }
        if (animalAtualizado.getPorte() != null) {
            animal.setPorte(animalAtualizado.getPorte());
        }
        if (animalAtualizado.getDataAdocao() != null) {
            animal.adotar(animalAtualizado.getDataAdocao());
        }
        if (animalAtualizado.getDataObito() != null) {
            animal.registrarObito(animalAtualizado.getDataObito());
        }

        return animalRepository.save(animal);
    }
}
