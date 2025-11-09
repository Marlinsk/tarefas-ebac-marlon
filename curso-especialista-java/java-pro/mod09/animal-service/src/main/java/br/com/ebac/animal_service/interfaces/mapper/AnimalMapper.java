package br.com.ebac.animal_service.interfaces.mapper;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.repository.FuncionarioRepository;
import br.com.ebac.animal_service.interfaces.dto.AnimalRequestDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalResponseDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    private final FuncionarioRepository funcionarioRepository;

    public AnimalMapper(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Animal toEntity(AnimalRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioRecebedorId()).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + dto.funcionarioRecebedorId()));
        return new Animal(dto.nomeProvisorio(), dto.idadeEstimada(), dto.racaOuEspecie(), dto.dataEntrada(), dto.descricaoCondicao(), funcionario, dto.porte());
    }

    public Animal toEntity(AnimalUpdateDTO dto) {
        Animal animal = new Animal();
        animal.setNomeProvisorio(dto.nomeProvisorio());
        animal.setIdadeEstimada(dto.idadeEstimada());
        animal.setRacaOuEspecie(dto.racaOuEspecie());
        animal.setDescricaoCondicao(dto.descricaoCondicao());
        animal.setPorte(dto.porte());
        animal.setDataAdocao(dto.dataAdocao());
        animal.setDataObito(dto.dataObito());
        return animal;
    }

    public AnimalResponseDTO toResponseDTO(Animal animal) {
        return new AnimalResponseDTO(animal.getId(), animal.getNomeProvisorio(), animal.getIdadeEstimada(), animal.getRacaOuEspecie(), animal.getDataEntrada(), animal.getDataAdocao(), animal.getDescricaoCondicao(), animal.getFuncionarioRecebedor().getId(), animal.getFuncionarioRecebedor().getNome(), animal.getDataObito(), animal.getPorte(), animal.isAdotado(), animal.isDisponivel());
    }
}
