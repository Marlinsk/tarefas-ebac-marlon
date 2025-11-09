package br.com.ebac.animal_service.interfaces.controller;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.interfaces.dto.AnimalRequestDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalResponseDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalUpdateDTO;
import br.com.ebac.animal_service.interfaces.dto.EstatisticaResgateResponseDTO;
import br.com.ebac.animal_service.interfaces.mapper.AnimalMapper;
import br.com.ebac.animal_service.usecase.BuscarEstatisticasResgateUseCase;
import br.com.ebac.animal_service.usecase.CadastrarAnimalUseCase;
import br.com.ebac.animal_service.usecase.EditarAnimalUseCase;
import br.com.ebac.animal_service.usecase.ListarAnimaisAdotadosUseCase;
import br.com.ebac.animal_service.usecase.ListarAnimaisDisponiveisUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animais")
public class AnimalController {

    private final CadastrarAnimalUseCase cadastrarAnimalUseCase;
    private final EditarAnimalUseCase editarAnimalUseCase;
    private final ListarAnimaisDisponiveisUseCase listarAnimaisDisponiveisUseCase;
    private final ListarAnimaisAdotadosUseCase listarAnimaisAdotadosUseCase;
    private final BuscarEstatisticasResgateUseCase buscarEstatisticasResgateUseCase;
    private final AnimalMapper animalMapper;

    public AnimalController(
            CadastrarAnimalUseCase cadastrarAnimalUseCase,
            EditarAnimalUseCase editarAnimalUseCase,
            ListarAnimaisDisponiveisUseCase listarAnimaisDisponiveisUseCase,
            ListarAnimaisAdotadosUseCase listarAnimaisAdotadosUseCase,
            BuscarEstatisticasResgateUseCase buscarEstatisticasResgateUseCase,
            AnimalMapper animalMapper
    ) {
        this.cadastrarAnimalUseCase = cadastrarAnimalUseCase;
        this.editarAnimalUseCase = editarAnimalUseCase;
        this.listarAnimaisDisponiveisUseCase = listarAnimaisDisponiveisUseCase;
        this.listarAnimaisAdotadosUseCase = listarAnimaisAdotadosUseCase;
        this.buscarEstatisticasResgateUseCase = buscarEstatisticasResgateUseCase;
        this.animalMapper = animalMapper;
    }

    @PostMapping
    public ResponseEntity<AnimalResponseDTO> cadastrar(@Valid @RequestBody AnimalRequestDTO request) {
        Animal animal = animalMapper.toEntity(request);
        Animal animalSalvo = cadastrarAnimalUseCase.execute(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalMapper.toResponseDTO(animalSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> editar(@PathVariable Long id, @Valid @RequestBody AnimalUpdateDTO request) {
        Animal animalAtualizado = animalMapper.toEntity(request);
        Animal animal = editarAnimalUseCase.execute(id, animalAtualizado);
        return ResponseEntity.ok(animalMapper.toResponseDTO(animal));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<AnimalResponseDTO>> listarDisponiveis() {
        List<Animal> animais = listarAnimaisDisponiveisUseCase.execute();
        List<AnimalResponseDTO> response = animais.stream().map(animalMapper::toResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adotados")
    public ResponseEntity<List<AnimalResponseDTO>> listarAdotados() {
        List<Animal> animais = listarAnimaisAdotadosUseCase.execute();
        List<AnimalResponseDTO> response = animais.stream().map(animalMapper::toResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estatisticas/resgates")
    public ResponseEntity<List<EstatisticaResgateResponseDTO>> buscarEstatisticasResgates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<EstatisticaResgateResponseDTO> estatisticas = buscarEstatisticasResgateUseCase.execute(dataInicio, dataFim);
        return ResponseEntity.ok(estatisticas);
    }
}
