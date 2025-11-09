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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarAnimaisAdotadosUseCaseTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private ListarAnimaisAdotadosUseCase listarAnimaisAdotadosUseCase;

    private Funcionario funcionario1;
    private Funcionario funcionario2;

    @BeforeEach
    void setUp() {
        funcionario1 = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario1.setId(1L);

        funcionario2 = new Funcionario("Maria Santos", "120.232.298-52", "Cuidadora", "(11) 94858-9088", "maria@abrigo.com");
        funcionario2.setId(2L);
    }

    @Test
    void deveListarAnimaisAdotadosComSucesso() {
        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(60), "Saudável", funcionario1, Porte.MEDIO);
        animal1.adotar(LocalDate.now().minusDays(10));

        Animal animal2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(50), "Saudável", funcionario2, Porte.PEQUENO);
        animal2.adotar(LocalDate.now().minusDays(5));

        when(animalRepository.findAdotados()).thenReturn(Arrays.asList(animal1, animal2));

        List<Animal> resultado = listarAnimaisAdotadosUseCase.execute();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).isAdotado());
        assertTrue(resultado.get(1).isAdotado());
        verify(animalRepository, times(1)).findAdotados();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverAnimaisAdotados() {
        when(animalRepository.findAdotados()).thenReturn(List.of());

        List<Animal> resultado = listarAnimaisAdotadosUseCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(animalRepository, times(1)).findAdotados();
    }
}
