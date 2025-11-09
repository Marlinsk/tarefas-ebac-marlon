package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import br.com.ebac.animal_service.usecase.exception.AnimalNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditarAnimalUseCaseTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private EditarAnimalUseCase editarAnimalUseCase;

    private Animal animalExistente;
    private Animal animalAtualizado;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario.setId(1L);

        animalExistente = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario, Porte.MEDIO);
        animalExistente.setId(1L);

        animalAtualizado = new Animal();
        animalAtualizado.setNomeProvisorio("Max");
        animalAtualizado.setIdadeEstimada(4);
    }

    @Test
    void deveEditarAnimalComSucesso() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalExistente));
        when(animalRepository.save(any(Animal.class))).thenReturn(animalExistente);

        Animal resultado = editarAnimalUseCase.execute(1L, animalAtualizado);

        assertNotNull(resultado);
        assertEquals("Max", resultado.getNomeProvisorio());
        assertEquals(4, resultado.getIdadeEstimada());
        verify(animalRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(animalExistente);
    }

    @Test
    void deveLancarExcecaoQuandoAnimalNaoEncontrado() {
        when(animalRepository.findById(999L)).thenReturn(Optional.empty());

        AnimalNotFoundException exception = assertThrows(AnimalNotFoundException.class, () -> editarAnimalUseCase.execute(999L, animalAtualizado));

        assertEquals("Animal com ID 999 não encontrado", exception.getMessage());
        verify(animalRepository, times(1)).findById(999L);
        verify(animalRepository, never()).save(any(Animal.class));
    }

    @Test
    void deveAdotarAnimalAoEditarComDataAdocao() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalExistente));
        when(animalRepository.save(any(Animal.class))).thenReturn(animalExistente);

        Animal animalComAdocao = new Animal();
        animalComAdocao.setDataAdocao(LocalDate.now());

        Animal resultado = editarAnimalUseCase.execute(1L, animalComAdocao);

        assertNotNull(resultado.getDataAdocao());
        assertTrue(resultado.isAdotado());
    }
}
