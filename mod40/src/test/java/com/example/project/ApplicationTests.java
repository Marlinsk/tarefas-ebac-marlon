package com.example.project;

import com.example.project.entities.Location;
import com.example.project.repositories.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @DisplayName("Deve salvar uma nova localização")
    void shouldSaveLocation() {
        Location location = new Location("America/Sao_Paulo", "São Paulo", "Brasil", -23.5505, -46.6333);
        Location saved = locationRepository.save(location);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCityName()).isEqualTo("São Paulo");
    }

    @Test
    @DisplayName("Deve buscar localização por ID")
    void shouldFindLocationById() {
        Location location = locationRepository.save(new Location("UTC", "Brasília", "Brasil", -15.7939, -47.8828));
        Optional<Location> found = locationRepository.findById(location.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCityName()).isEqualTo("Brasília");
    }

    @Test
    @DisplayName("Deve listar todas as localizações")
    void shouldListAllLocations() {
        locationRepository.save(new Location("UTC", "Rio de Janeiro", "Brasil", -22.9068, -43.1729));
        locationRepository.save(new Location("UTC", "Recife", "Brasil", -8.0476, -34.8770));

        List<Location> all = locationRepository.findAll();
        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("Deve atualizar uma localização existente")
    void shouldUpdateLocation() {
        Location location = locationRepository.save(new Location("UTC", "Salvador", "Brasil", -12.9718, -38.5011));
        location.setCityName("Salvador da Bahia");

        Location updated = locationRepository.save(location);
        assertThat(updated.getCityName()).isEqualTo("Salvador da Bahia");
    }

    @Test
    @DisplayName("Deve deletar uma localização pelo ID")
    void shouldDeleteLocationById() {
        Location location = locationRepository.save(new Location("UTC", "Fortaleza", "Brasil", -3.7172, -38.5433));
        Long id = location.getId();

        locationRepository.deleteById(id);
        Optional<Location> deleted = locationRepository.findById(id);

        assertThat(deleted).isEmpty();
    }
}
