package io.playground.catalogservice.application.dto;

import java.math.BigDecimal;

public record Snapshot(
        Long productId,
        String productName,
        Long variantId,
        String variantName,
        BigDecimal price
) {
}
