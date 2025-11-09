package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import br.com.ebac.animal_service.interfaces.dto.EstatisticaResgateResponseDTO;
import br.com.ebac.animal_service.usecase.exception.IntervaloDataInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class BuscarEstatisticasResgateUseCaseTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private BuscarEstatisticasResgateUseCase useCase;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Funcionario funcionario1;
    private Funcionario funcionario2;

    @BeforeEach
    void setUp() {
        dataInicio = LocalDate.of(2024, 1, 1);
        dataFim = LocalDate.of(2024, 12, 31);

        funcionario1 = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario1.setId(1L);

        funcionario2 = new Funcionario("Maria Santos", "120.232.298-52", "Cuidadora", "(11) 94858-9088", "maria@abrigo.com");
        funcionario2.setId(2L);
    }

    @Test
    @DisplayName("Deve retornar estatísticas agrupadas por funcionário")
    void deveRetornarEstatisticasAgrupadasPorFuncionario() {
        Animal animal1 = criarAnimal("Rex", funcionario1, dataInicio);
        Animal animal2 = criarAnimal("Bella", funcionario1, dataInicio.plusDays(1));
        Animal animal3 = criarAnimal("Max", funcionario2, dataInicio.plusDays(2));
        Animal animal4 = criarAnimal("Luna", funcionario1, dataInicio.plusDays(3));

        when(animalRepository.findByDataEntradaBetween(dataInicio, dataFim)).thenReturn(Arrays.asList(animal1, animal2, animal3, animal4));

        List<EstatisticaResgateResponseDTO> resultado = useCase.execute(dataInicio, dataFim);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Verifica ordenação decrescente por quantidade
        assertEquals("João Silva", resultado.get(0).nomeFuncionario());
        assertEquals(3L, resultado.get(0).quantidadeAnimaisResgatados());
        assertEquals("Maria Santos", resultado.get(1).nomeFuncionario());
        assertEquals(1L, resultado.get(1).quantidadeAnimaisResgatados());

        verify(animalRepository, times(1)).findByDataEntradaBetween(dataInicio, dataFim);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver animais no período")
    void deveRetornarListaVaziaQuandoNaoHouverAnimais() {
        when(animalRepository.findByDataEntradaBetween(dataInicio, dataFim)).thenReturn(Arrays.asList());

        List<EstatisticaResgateResponseDTO> resultado = useCase.execute(dataInicio, dataFim);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(animalRepository, times(1)).findByDataEntradaBetween(dataInicio, dataFim);
    }

    @Test
    @DisplayName("Deve lançar exceção quando data início é nula")
    void deveLancarExcecaoQuandoDataInicioNula() {
        IntervaloDataInvalidoException exception = assertThrows(IntervaloDataInvalidoException.class, () -> useCase.execute(null, dataFim));
        assertEquals("As datas de início e fim são obrigatórias", exception.getMessage());
        verify(animalRepository, never()).findByDataEntradaBetween(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando data fim é nula")
    void deveLancarExcecaoQuandoDataFimNula() {
        IntervaloDataInvalidoException exception = assertThrows(IntervaloDataInvalidoException.class, () -> useCase.execute(dataInicio, null));
        assertEquals("As datas de início e fim são obrigatórias", exception.getMessage());
        verify(animalRepository, never()).findByDataEntradaBetween(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando data início é posterior à data fim")
    void deveLancarExcecaoQuandoDataInicioMaiorQueDataFim() {
        LocalDate dataInicioInvalida = LocalDate.of(2024, 12, 31);
        LocalDate dataFimInvalida = LocalDate.of(2024, 1, 1);

        IntervaloDataInvalidoException exception = assertThrows(IntervaloDataInvalidoException.class, () -> useCase.execute(dataInicioInvalida, dataFimInvalida));

        assertEquals("A data de início não pode ser posterior à data de fim", exception.getMessage());
        verify(animalRepository, never()).findByDataEntradaBetween(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando intervalo é maior que 1 ano")
    void deveLancarExcecaoQuandoIntervaloMaiorQueUmAno() {
        LocalDate dataInicioInvalida = LocalDate.of(2024, 1, 1);
        LocalDate dataFimInvalida = LocalDate.of(2025, 1, 2); // 366 dias

        IntervaloDataInvalidoException exception = assertThrows(IntervaloDataInvalidoException.class, () -> useCase.execute(dataInicioInvalida, dataFimInvalida));

        assertTrue(exception.getMessage().contains("O intervalo máximo permitido é de 365 dias"));
        verify(animalRepository, never()).findByDataEntradaBetween(any(), any());
    }

    @Test
    @DisplayName("Deve ordenar resultado por quantidade decrescente")
    void deveOrdenarResultadoPorQuantidadeDecrescente() {
        Animal animal1 = criarAnimal("Rex", funcionario1, dataInicio);
        Animal animal2 = criarAnimal("Bella", funcionario2, dataInicio.plusDays(1));
        Animal animal3 = criarAnimal("Max", funcionario2, dataInicio.plusDays(2));
        Animal animal4 = criarAnimal("Luna", funcionario2, dataInicio.plusDays(3));
        Animal animal5 = criarAnimal("Thor", funcionario1, dataInicio.plusDays(4));

        when(animalRepository.findByDataEntradaBetween(dataInicio, dataFim)).thenReturn(Arrays.asList(animal1, animal2, animal3, animal4, animal5));

        List<EstatisticaResgateResponseDTO> resultado = useCase.execute(dataInicio, dataFim);

        assertEquals(2, resultado.size());
        assertEquals(3L, resultado.get(0).quantidadeAnimaisResgatados());
        assertEquals(2L, resultado.get(1).quantidadeAnimaisResgatados());
    }

    private Animal criarAnimal(String nome, Funcionario funcionario, LocalDate dataEntrada) {
        Animal animal = new Animal(nome, 2, "Cachorro", dataEntrada, "Saudável", funcionario, Porte.MEDIO);
        return animal;
    }
}
