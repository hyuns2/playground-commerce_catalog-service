package io.playground.catalogservice.application.dto;

import java.math.BigDecimal;

public record ProductSearchResult(
        Long id,
        String name,
        BigDecimal minPrice,
        String description
) {
}
