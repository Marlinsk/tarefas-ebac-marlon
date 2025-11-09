package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarAnimalUseCaseTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private CadastrarAnimalUseCase cadastrarAnimalUseCase;

    private Animal animal;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario.setId(1L);

        animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
    }

    @Test
    void deveCadastrarAnimalComSucesso() {
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        Animal resultado = cadastrarAnimalUseCase.execute(animal);

        assertNotNull(resultado);
        assertEquals("Rex", resultado.getNomeProvisorio());
        verify(animalRepository, times(1)).save(animal);
    }
}
