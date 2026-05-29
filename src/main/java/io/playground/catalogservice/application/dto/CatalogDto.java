package io.playground.catalogservice.application.dto;

import io.playground.catalogservice.domain.Variant;

import java.math.BigDecimal;

public class CatalogDto {
    public record VariantInfo(
            Long id,
            String name,
            BigDecimal price,
            int available
    ) {
        public static VariantInfo from(Variant variant,
                                       int available) {
            return new VariantInfo(
                    variant.getId(),
                    variant.getName(),
                    variant.getPrice(),
                    available
            );
        }
    }
}
