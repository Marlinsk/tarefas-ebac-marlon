package com.example.project.controller;

import com.example.project.LocationMapper;
import com.example.project.dtos.LocationRequest;
import com.example.project.dtos.LocationResponse;
import com.example.project.services.LocationService;
import com.example.project.entities.Location;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/locations")
@Tag(name = "Localizações", description = "Gerenciamento de cidades, países e fusos horários")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    private static final Set<String> ALLOWED_SORTS = Set.of("id","cityName","countryName","timezone","latitude","longitude");

    @GetMapping
    @Operation(summary = "Listar localizações com paginação", description = "Retorna a lista paginada de localizações. Redireciona automaticamente se nenhum parâmetro for informado.")
    public Object redirectOrList(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "cityName", direction = Sort.Direction.ASC) Pageable pageable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getQueryString() == null || request.getQueryString().isBlank()) {
            response.sendRedirect("/locations?page=0&size=10&sort=cityName,asc");
            return null;
        }

        List<Sort.Order> orders = pageable.getSort().stream().filter(o -> ALLOWED_SORTS.contains(o.getProperty())).collect(Collectors.toList());
        Sort safeSort = orders.isEmpty() ? Sort.by(Sort.Order.asc("cityName")) : Sort.by(orders);
        PageRequest safePageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), safeSort);

        return service.findAll(safePageable).map(LocationMapper::toResponse);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Pegar uma localização pelo id")
    public ResponseEntity<LocationResponse> getById(@PathVariable Long id) {
        return service.findById(id).map(LocationMapper::toResponse).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @Operation(summary = "Criar uma nova localização")
    public ResponseEntity<LocationResponse> create(@RequestBody LocationRequest req) {
        Location location = LocationMapper.toEntity(req);
        Location saved = service.save(location);
        return ResponseEntity.created(URI.create("/locations/" + saved.getId())).body(LocationMapper.toResponse(saved));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar dados de uma localização")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable Long id, @RequestBody LocationRequest req) {
        Optional<Location> existing = service.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Location updated = existing.get();
        updated.setTimezone(req.timezone);
        updated.setCityName(req.cityName);
        updated.setCountryName(req.countryName);
        updated.setLatitude(req.latitude);
        updated.setLongitude(req.longitude);

        return ResponseEntity.ok(LocationMapper.toResponse(service.save(updated)));
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "Remover uma localização")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
