package br.com.ebac.animal_service.interfaces.mapper;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import br.com.ebac.animal_service.domain.repository.FuncionarioRepository;
import br.com.ebac.animal_service.interfaces.dto.AnimalRequestDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalResponseDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalMapperTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private AnimalMapper animalMapper;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario.setId(1L);
    }

    @Test
    void deveConverterRequestDTOParaEntidade() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        AnimalRequestDTO dto = new AnimalRequestDTO("Rex", 3, "Cachorro", LocalDate.of(2025, 1, 1), "Saudável", 1L, Porte.MEDIO);

        Animal animal = animalMapper.toEntity(dto);

        assertNotNull(animal);
        assertEquals("Rex", animal.getNomeProvisorio());
        assertEquals(3, animal.getIdadeEstimada());
        assertEquals("Cachorro", animal.getRacaOuEspecie());
        assertEquals(LocalDate.of(2025, 1, 1), animal.getDataEntrada());
        assertEquals("Saudável", animal.getDescricaoCondicao());
        assertEquals(funcionario, animal.getFuncionarioRecebedor());
        assertEquals(Porte.MEDIO, animal.getPorte());
        assertNull(animal.getId());
        assertNull(animal.getDataAdocao());
        assertNull(animal.getDataObito());
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    void deveConverterUpdateDTOParaEntidade() {
        AnimalUpdateDTO dto = new AnimalUpdateDTO("Max", 4, "Gato", "Recuperado", Porte.PEQUENO, LocalDate.of(2025, 1, 15), null);

        Animal animal = animalMapper.toEntity(dto);

        assertNotNull(animal);
        assertEquals("Max", animal.getNomeProvisorio());
        assertEquals(4, animal.getIdadeEstimada());
        assertEquals("Gato", animal.getRacaOuEspecie());
        assertEquals("Recuperado", animal.getDescricaoCondicao());
        assertEquals(Porte.PEQUENO, animal.getPorte());
        assertEquals(LocalDate.of(2025, 1, 15), animal.getDataAdocao());
        assertNull(animal.getDataObito());
    }

    @Test
    void deveConverterEntidadeParaResponseDTO() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.of(2025, 1, 1), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);

        AnimalResponseDTO dto = animalMapper.toResponseDTO(animal);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Rex", dto.nomeProvisorio());
        assertEquals(3, dto.idadeEstimada());
        assertEquals("Cachorro", dto.racaOuEspecie());
        assertEquals(LocalDate.of(2025, 1, 1), dto.dataEntrada());
        assertEquals("Saudável", dto.descricaoCondicao());
        assertEquals(1L, dto.funcionarioRecebedorId());
        assertEquals("João Silva", dto.nomeFuncionarioRecebedor());
        assertEquals(Porte.MEDIO, dto.porte());
        assertNull(dto.dataAdocao());
        assertNull(dto.dataObito());
        assertFalse(dto.adotado());
        assertTrue(dto.disponivel());
    }

    @Test
    void deveConverterEntidadeAdotadaParaResponseDTO() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.of(2025, 1, 1), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);
        animal.adotar(LocalDate.of(2025, 1, 15));

        AnimalResponseDTO dto = animalMapper.toResponseDTO(animal);

        assertNotNull(dto);
        assertEquals(LocalDate.of(2025, 1, 15), dto.dataAdocao());
        assertTrue(dto.adotado());
        assertFalse(dto.disponivel());
    }

    @Test
    void deveLancarExcecaoQuandoFuncionarioNaoEncontrado() {
        when(funcionarioRepository.findById(999L)).thenReturn(Optional.empty());

        AnimalRequestDTO dto = new AnimalRequestDTO("Rex", 3, "Cachorro", LocalDate.of(2025, 1, 1), "Saudável", 999L, Porte.MEDIO);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> animalMapper.toEntity(dto));

        assertEquals("Funcionário não encontrado com ID: 999", exception.getMessage());
        verify(funcionarioRepository, times(1)).findById(999L);
    }

    @Test
    void deveConverterUpdateDTOComCamposNulosParaEntidade() {
        AnimalUpdateDTO dto = new AnimalUpdateDTO(null, null, null, null, null, null, null);

        Animal animal = animalMapper.toEntity(dto);

        assertNotNull(animal);
        assertNull(animal.getNomeProvisorio());
        assertNull(animal.getIdadeEstimada());
        assertNull(animal.getRacaOuEspecie());
        assertNull(animal.getDescricaoCondicao());
        assertNull(animal.getPorte());
        assertNull(animal.getDataAdocao());
        assertNull(animal.getDataObito());
    }
}
