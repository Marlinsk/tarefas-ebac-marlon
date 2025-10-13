package com.example.project.services;

import com.example.project.repositories.LocationRepository;
import com.example.project.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public Page<Location> findAll(@PageableDefault(page = 0, size = 10, sort = "cityName", direction = Sort.Direction.ASC) Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Location> findById(Long id) {
        return repository.findById(id);
    }

    public Location save(Location location) {
        return repository.save(location);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
