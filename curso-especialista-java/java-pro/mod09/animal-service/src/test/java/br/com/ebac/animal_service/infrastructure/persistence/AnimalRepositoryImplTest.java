package br.com.ebac.animal_service.infrastructure.persistence;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalRepositoryImplTest {

    @Mock
    private AnimalJpaRepository jpaRepository;

    @InjectMocks
    private AnimalRepositoryImpl animalRepository;

    private Animal animal;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario.setId(1L);

        animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);
    }

    @Test
    void deveSalvarAnimal() {
        when(jpaRepository.save(animal)).thenReturn(animal);

        Animal salvo = animalRepository.save(animal);

        assertNotNull(salvo);
        assertEquals(animal.getId(), salvo.getId());
        verify(jpaRepository, times(1)).save(animal);
    }

    @Test
    void deveBuscarAnimalPorId() {
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(animal));

        Optional<Animal> encontrado = animalRepository.findById(1L);

        assertTrue(encontrado.isPresent());
        assertEquals(animal.getId(), encontrado.get().getId());
        verify(jpaRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoAnimalNaoEncontrado() {
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Animal> encontrado = animalRepository.findById(999L);

        assertFalse(encontrado.isPresent());
        verify(jpaRepository, times(1)).findById(999L);
    }

    @Test
    void deveListarTodosAnimais() {
        List<Animal> animais = Arrays.asList(animal);
        when(jpaRepository.findAll()).thenReturn(animais);

        List<Animal> resultado = animalRepository.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(jpaRepository, times(1)).findAll();
    }

    @Test
    void deveListarAnimaisDisponiveis() {
        List<Animal> animais = Arrays.asList(animal);
        when(jpaRepository.findDisponiveis()).thenReturn(animais);

        List<Animal> resultado = animalRepository.findDisponiveis();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(jpaRepository, times(1)).findDisponiveis();
    }

    @Test
    void deveListarAnimaisAdotados() {
        animal.adotar(LocalDate.now());
        List<Animal> animais = Arrays.asList(animal);
        when(jpaRepository.findAdotados()).thenReturn(animais);

        List<Animal> resultado = animalRepository.findAdotados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isAdotado());
        verify(jpaRepository, times(1)).findAdotados();
    }

    @Test
    void deveDeletarAnimalPorId() {
        doNothing().when(jpaRepository).deleteById(1L);

        animalRepository.deleteById(1L);

        verify(jpaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveVerificarSeAnimalExiste() {
        when(jpaRepository.existsById(1L)).thenReturn(true);
        when(jpaRepository.existsById(999L)).thenReturn(false);

        assertTrue(animalRepository.existsById(1L));
        assertFalse(animalRepository.existsById(999L));

        verify(jpaRepository, times(1)).existsById(1L);
        verify(jpaRepository, times(1)).existsById(999L);
    }
}
