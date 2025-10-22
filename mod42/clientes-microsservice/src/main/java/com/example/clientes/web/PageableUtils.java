package com.example.clientes.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PageableUtils {
    private PageableUtils() {}

    public static Pageable sanitize(Pageable pageable, Set<String> allowedSorts, Sort fallbackSort) {
        List<Sort.Order> safeOrders = pageable.getSort().stream().filter(o -> allowedSorts.contains(o.getProperty())).collect(Collectors.toList());
        Sort safeSort = safeOrders.isEmpty() ? fallbackSort : Sort.by(safeOrders);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), safeSort);
    }
}
