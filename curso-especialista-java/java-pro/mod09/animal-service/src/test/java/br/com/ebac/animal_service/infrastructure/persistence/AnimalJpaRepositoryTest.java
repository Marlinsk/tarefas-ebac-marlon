package br.com.ebac.animal_service.infrastructure.persistence;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
class AnimalJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnimalJpaRepository animalJpaRepository;

    private Funcionario funcionario1;
    private Funcionario funcionario2;
    private Funcionario funcionario3;

    @BeforeEach
    void setUp() {
        animalJpaRepository.deleteAll();

        funcionario1 = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario1 = entityManager.persistAndFlush(funcionario1);

        funcionario2 = new Funcionario("Maria Santos", "120.232.298-52", "Cuidadora", "(11) 94858-9088", "maria@abrigo.com");
        funcionario2 = entityManager.persistAndFlush(funcionario2);

        funcionario3 = new Funcionario("Pedro Oliveira", "121.727.800-89", "Assistente", "(11) 99981-1785", "pedro@abrigo.com");
        funcionario3 = entityManager.persistAndFlush(funcionario3);
    }

    @Test
    void deveSalvarAnimal() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);

        Animal salvo = animalJpaRepository.save(animal);

        assertNotNull(salvo.getId());
        assertEquals("Rex", salvo.getNomeProvisorio());
    }

    @Test
    void deveBuscarAnimalPorId() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);
        Animal salvo = entityManager.persistAndFlush(animal);

        Animal encontrado = animalJpaRepository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Rex", encontrado.getNomeProvisorio());
    }

    @Test
    void deveListarAnimaisDisponiveis() {
        // Animal disponível
        Animal disponivel1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario1, Porte.MEDIO);
        entityManager.persistAndFlush(disponivel1);

        // Outro animal disponível
        Animal disponivel2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(20), "Saudável", funcionario2, Porte.PEQUENO);
        entityManager.persistAndFlush(disponivel2);

        // Animal adotado (não deve aparecer)
        Animal adotado = new Animal("Bob", 5, "Cachorro", LocalDate.now().minusDays(60), "Saudável", funcionario3, Porte.GRANDE);
        adotado.adotar(LocalDate.now().minusDays(10));
        entityManager.persistAndFlush(adotado);

        // Animal falecido (não deve aparecer)
        Animal falecido = new Animal("Luna", 1, "Gato", LocalDate.now().minusDays(50), "Saudável", funcionario1, Porte.PEQUENO);
        falecido.registrarObito(LocalDate.now().minusDays(5));
        entityManager.persistAndFlush(falecido);

        List<Animal> disponiveis = animalJpaRepository.findDisponiveis();

        assertEquals(2, disponiveis.size());
        // Verifica ordenação por data de entrada (mais antigo primeiro)
        assertEquals("Rex", disponiveis.get(0).getNomeProvisorio());
        assertEquals("Mia", disponiveis.get(1).getNomeProvisorio());
    }

    @Test
    void deveListarAnimaisAdotados() {
        // Animal adotado recentemente
        Animal adotado1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(60), "Saudável", funcionario1, Porte.MEDIO);
        adotado1.adotar(LocalDate.now().minusDays(5));
        entityManager.persistAndFlush(adotado1);

        // Animal adotado há mais tempo
        Animal adotado2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(50), "Saudável", funcionario2, Porte.PEQUENO);
        adotado2.adotar(LocalDate.now().minusDays(10));
        entityManager.persistAndFlush(adotado2);

        // Animal disponível (não deve aparecer)
        Animal disponivel = new Animal("Bob", 5, "Cachorro", LocalDate.now().minusDays(40), "Saudável", funcionario3, Porte.GRANDE);
        entityManager.persistAndFlush(disponivel);

        // Animal falecido após adoção (não deve aparecer)
        Animal falecidoAposAdocao = new Animal("Luna", 1, "Gato", LocalDate.now().minusDays(80), "Saudável", funcionario1, Porte.PEQUENO);
        falecidoAposAdocao.adotar(LocalDate.now().minusDays(20));
        falecidoAposAdocao.registrarObito(LocalDate.now().minusDays(5));
        entityManager.persistAndFlush(falecidoAposAdocao);

        List<Animal> adotados = animalJpaRepository.findAdotados();

        assertEquals(2, adotados.size());
        // Verifica ordenação por data de adoção (mais recente primeiro)
        assertEquals("Rex", adotados.get(0).getNomeProvisorio());
        assertEquals("Mia", adotados.get(1).getNomeProvisorio());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverAnimaisDisponiveis() {
        List<Animal> disponiveis = animalJpaRepository.findDisponiveis();

        assertTrue(disponiveis.isEmpty());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverAnimaisAdotados() {
        List<Animal> adotados = animalJpaRepository.findAdotados();

        assertTrue(adotados.isEmpty());
    }

    @Test
    void deveDeletarAnimal() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);
        Animal salvo = entityManager.persistAndFlush(animal);

        animalJpaRepository.deleteById(salvo.getId());

        Animal encontrado = animalJpaRepository.findById(salvo.getId()).orElse(null);
        assertNull(encontrado);
    }

    @Test
    void deveVerificarSeAnimalExiste() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);
        Animal salvo = entityManager.persistAndFlush(animal);

        assertTrue(animalJpaRepository.existsById(salvo.getId()));
        assertFalse(animalJpaRepository.existsById(999L));
    }

    @Test
    void deveAtualizarAnimal() {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);
        Animal salvo = entityManager.persistAndFlush(animal);

        salvo.setNomeProvisorio("Max");
        salvo.setIdadeEstimada(4);
        Animal atualizado = animalJpaRepository.save(salvo);

        assertEquals("Max", atualizado.getNomeProvisorio());
        assertEquals(4, atualizado.getIdadeEstimada());
    }
}
