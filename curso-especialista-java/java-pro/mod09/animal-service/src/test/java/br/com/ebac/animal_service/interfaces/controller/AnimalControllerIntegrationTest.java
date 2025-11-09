package br.com.ebac.animal_service.interfaces.controller;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.entity.Funcionario;
import br.com.ebac.animal_service.domain.enums.Porte;
import br.com.ebac.animal_service.infrastructure.persistence.AnimalJpaRepository;
import br.com.ebac.animal_service.infrastructure.persistence.FuncionarioJpaRepository;
import br.com.ebac.animal_service.interfaces.dto.AnimalRequestDTO;
import br.com.ebac.animal_service.interfaces.dto.AnimalUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnimalJpaRepository animalJpaRepository;

    @Autowired
    private FuncionarioJpaRepository funcionarioJpaRepository;

    private Funcionario funcionario1;
    private Funcionario funcionario2;
    private Funcionario funcionario3;

    @BeforeEach
    void setUp() {
        animalJpaRepository.deleteAll();
        funcionarioJpaRepository.deleteAll();

        funcionario1 = new Funcionario("João Silva", "111.234.196-11", "Veterinário", "(11) 9956-11069", "joao@abrigo.com");
        funcionario1 = funcionarioJpaRepository.save(funcionario1);

        funcionario2 = new Funcionario("Maria Santos", "120.232.298-52", "Cuidadora", "(11) 94858-9088", "maria@abrigo.com");
        funcionario2 = funcionarioJpaRepository.save(funcionario2);

        funcionario3 = new Funcionario("Pedro Oliveira", "121.727.800-89", "Assistente", "(11) 99981-1785", "pedro@abrigo.com");
        funcionario3 = funcionarioJpaRepository.save(funcionario3);
    }

    @Test
    void deveCadastrarAnimalComSucesso() throws Exception {
        AnimalRequestDTO request = new AnimalRequestDTO(
                "Rex",
                3,
                "Cachorro",
                LocalDate.now(),
                "Saudável",
                funcionario1.getId(),
                Porte.MEDIO
        );

        mockMvc.perform(post("/api/animais").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeProvisorio").value("Rex"))
                .andExpect(jsonPath("$.idadeEstimada").value(3))
                .andExpect(jsonPath("$.racaOuEspecie").value("Cachorro"))
                .andExpect(jsonPath("$.porte").value("MEDIO"))
                .andExpect(jsonPath("$.funcionarioRecebedorId").value(funcionario1.getId()))
                .andExpect(jsonPath("$.nomeFuncionarioRecebedor").value("João Silva"))
                .andExpect(jsonPath("$.disponivel").value(true))
                .andExpect(jsonPath("$.adotado").value(false));
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoCamposObrigatoriosVazios() throws Exception {
        AnimalRequestDTO request = new AnimalRequestDTO("", null, "", null, "Saudável", null, null);

        mockMvc.perform(post("/api/animais").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors", hasSize(greaterThan(0))));
    }

    @Test
    void deveEditarAnimalComSucesso() throws Exception {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now(), "Saudável", funcionario1, Porte.MEDIO);
        animal = animalJpaRepository.save(animal);

        AnimalUpdateDTO updateDTO = new AnimalUpdateDTO("Max", 4, "Cachorro", "Recuperado", Porte.GRANDE, null, null);

        mockMvc.perform(put("/api/animais/" + animal.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(animal.getId()))
                .andExpect(jsonPath("$.nomeProvisorio").value("Max"))
                .andExpect(jsonPath("$.idadeEstimada").value(4))
                .andExpect(jsonPath("$.porte").value("GRANDE"));
    }

    @Test
    void deveRetornarNotFoundAoEditarAnimalInexistente() throws Exception {
        AnimalUpdateDTO updateDTO = new AnimalUpdateDTO("Max", 4, "Cachorro", "Recuperado", Porte.GRANDE, null, null);

        mockMvc.perform(put("/api/animais/999").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Animal com ID 999 não encontrado"));
    }

    @Test
    void deveListarAnimaisDisponiveisOrdenadosPorDataEntrada() throws Exception {
        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario1, Porte.MEDIO);
        Animal animal2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(20), "Saudável", funcionario2, Porte.PEQUENO);
        Animal animal3 = new Animal("Bob", 5, "Cachorro", LocalDate.now().minusDays(10), "Saudável", funcionario3, Porte.GRANDE);

        animalJpaRepository.save(animal1);
        animalJpaRepository.save(animal2);
        animalJpaRepository.save(animal3);

        mockMvc.perform(get("/api/animais/disponiveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nomeProvisorio").value("Rex"))  // Mais antigo primeiro
                .andExpect(jsonPath("$[1].nomeProvisorio").value("Mia"))
                .andExpect(jsonPath("$[2].nomeProvisorio").value("Bob"));
    }

    @Test
    void deveListarApenasAnimaisDisponiveisExcluindoAdotados() throws Exception {
        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario1, Porte.MEDIO);
        Animal animal2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(20), "Saudável", funcionario2, Porte.PEQUENO);
        animal2.adotar(LocalDate.now());

        animalJpaRepository.save(animal1);
        animalJpaRepository.save(animal2);

        mockMvc.perform(get("/api/animais/disponiveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nomeProvisorio").value("Rex"))
                .andExpect(jsonPath("$[0].disponivel").value(true));
    }

    @Test
    void deveListarAnimaisAdotados() throws Exception {
        Animal animal1 = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(60), "Saudável", funcionario1, Porte.MEDIO);
        animal1.adotar(LocalDate.now().minusDays(10));

        Animal animal2 = new Animal("Mia", 2, "Gato", LocalDate.now().minusDays(50), "Saudável", funcionario2, Porte.PEQUENO);
        animal2.adotar(LocalDate.now().minusDays(5));

        Animal animal3 = new Animal("Bob", 5, "Cachorro", LocalDate.now().minusDays(40), "Saudável", funcionario3, Porte.GRANDE);

        animalJpaRepository.save(animal1);
        animalJpaRepository.save(animal2);
        animalJpaRepository.save(animal3);

        mockMvc.perform(get("/api/animais/adotados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].adotado").value(true))
                .andExpect(jsonPath("$[1].adotado").value(true));
    }

    @Test
    void deveAdotarAnimalAoEditar() throws Exception {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario1, Porte.MEDIO);
        animal = animalJpaRepository.save(animal);

        AnimalUpdateDTO updateDTO = new AnimalUpdateDTO(null, null, null, null, null, LocalDate.now(), null);

        mockMvc.perform(put("/api/animais/" + animal.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adotado").value(true))
                .andExpect(jsonPath("$.disponivel").value(false))
                .andExpect(jsonPath("$.dataAdocao").exists());
    }

    @Test
    void deveRetornarBadRequestAoAdotarAnimalJaAdotado() throws Exception {
        Animal animal = new Animal("Rex", 3, "Cachorro", LocalDate.now().minusDays(30), "Saudável", funcionario1, Porte.MEDIO);
        animal.adotar(LocalDate.now().minusDays(10));
        animal = animalJpaRepository.save(animal);

        AnimalUpdateDTO updateDTO = new AnimalUpdateDTO(null, null, null, null, null, LocalDate.now(), null);

        mockMvc.perform(put("/api/animais/" + animal.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Animal já foi adotado"));
    }
}
