package br.com.ebac.animal_service.domain.entity;

import br.com.ebac.animal_service.domain.enums.Porte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario.setId(1L);
    }

    @Test
    void deveCriarAnimalComSucesso() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);

        assertNotNull(animal);
        assertEquals("Rex", animal.getNomeProvisorio());
        assertEquals(3, animal.getIdadeEstimada());
        assertEquals("Cachorro", animal.getRacaOuEspecie());
        assertEquals(Porte.MEDIO, animal.getPorte());
        assertEquals(funcionario, animal.getFuncionarioRecebedor());
    }

    @Test
    void deveRetornarTrueParaIsDisponivel() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);

        assertTrue(animal.isDisponivel());
        assertFalse(animal.isAdotado());
    }

    @Test
    void deveRetornarFalseParaIsDisponivelQuandoAdotado() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.adotar(LocalDate.now());

        assertFalse(animal.isDisponivel());
        assertTrue(animal.isAdotado());
    }

    @Test
    void deveAdotarAnimalComSucesso() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);

        LocalDate dataAdocao = LocalDate.now();
        animal.adotar(dataAdocao);

        assertEquals(dataAdocao, animal.getDataAdocao());
        assertTrue(animal.isAdotado());
    }

    @Test
    void deveLancarExcecaoAoAdotarAnimalJaAdotado() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.adotar(LocalDate.now());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> animal.adotar(LocalDate.now()));

        assertEquals("Animal já foi adotado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoAdotarAnimalFalecido() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.registrarObito(LocalDate.now());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> animal.adotar(LocalDate.now()));

        assertEquals("Não é possível adotar um animal que já faleceu", exception.getMessage());
    }

    @Test
    void deveRegistrarObitoComSucesso() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);

        LocalDate dataObito = LocalDate.now();
        animal.registrarObito(dataObito);

        assertEquals(dataObito, animal.getDataObito());
        assertFalse(animal.isDisponivel());
        assertFalse(animal.isAdotado());
    }

    @Test
    void deveLancarExcecaoAoRegistrarObitoJaRegistrado() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.registrarObito(LocalDate.now());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> animal.registrarObito(LocalDate.now()));

        assertEquals("Óbito já registrado", exception.getMessage());
    }

    @Test
    void deveRetornarFalseParaIsAdotadoQuandoTiverObito() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.adotar(LocalDate.now().minusDays(10));
        animal.registrarObito(LocalDate.now());

        assertFalse(animal.isAdotado());
        assertFalse(animal.isDisponivel());
    }

    @Test
    void deveSerIgualQuandoTiverMesmoId() {
        Funcionario funcionario2 = new Funcionario("Maria Santos", "120.232.298-52", "Cuidadora", "(11) 94858-9088", "maria@abrigo.com");
        funcionario2.setId(2L);

        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal1.setId(1L);

        Animal animal2 = new Animal("Mia", 2, "Gato", LocalDate.now(), "Saudável", funcionario2, Porte.PEQUENO);
        animal2.setId(1L);

        assertEquals(animal1, animal2);
        assertEquals(animal1.hashCode(), animal2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoTiverIdsDiferentes() {
        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal1.setId(1L);

        Animal animal2 = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal2.setId(2L);

        assertNotEquals(animal1, animal2);
    }

    @Test
    void deveSerIgualAElesMesmo() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);

        assertEquals(animal, animal);
    }

    @Test
    void naoDeveSerIgualANull() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);

        assertNotEquals(animal, null);
    }

    @Test
    void naoDeveSerIgualAObjetoDiferente() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);

        assertNotEquals(animal, "String");
    }

    @Test
    void deveGerarToStringCorretamente() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario, Porte.MEDIO);
        animal.setId(1L);

        String toString = animal.toString();

        assertTrue(toString.contains("Animal{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nomeProvisorio='Rex'"));
        assertTrue(toString.contains("racaOuEspecie='Cachorro'"));
        assertTrue(toString.contains("porte=MEDIO"));
    }

    @Test
    void devePermitirSettersComSucesso() {
        Animal animal = new Animal();

        animal.setId(1L);
        animal.setNomeProvisorio("Rex");
        animal.setIdadeEstimada(3);
        animal.setRacaOuEspecie("Cachorro");
        animal.setDataEntrada(LocalDate.now());
        animal.setDescricaoCondicao("Saudável");
        animal.setFuncionarioRecebedor(funcionario);
        animal.setPorte(Porte.MEDIO);

        assertEquals(1L, animal.getId());
        assertEquals("Rex", animal.getNomeProvisorio());
        assertEquals(3, animal.getIdadeEstimada());
        assertEquals("Cachorro", animal.getRacaOuEspecie());
        assertNotNull(animal.getDataEntrada());
        assertEquals("Saudável", animal.getDescricaoCondicao());
        assertEquals(funcionario, animal.getFuncionarioRecebedor());
        assertEquals(Porte.MEDIO, animal.getPorte());
    }
}
