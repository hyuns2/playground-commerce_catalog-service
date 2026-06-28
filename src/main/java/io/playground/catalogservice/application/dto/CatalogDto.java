package io.playground.catalogservice.application.dto;

import io.playground.catalogservice.domain.Variant;
import lombok.Builder;

import java.math.BigDecimal;

public class CatalogDto {
    @Builder
    public record ProductSearchResult(
            Long id,
            String name,
            BigDecimal minPrice,
            String description
    ) {
    }

    public record VariantInfo(
            Long id,
            String name,
            BigDecimal price,
            Integer available
    ) {
        public static VariantInfo from(Variant variant,
                                       Integer available) {
            return new VariantInfo(
                    variant.getId(),
                    variant.getName(),
                    variant.getPrice(),
                    available
            );
        }
    }
}
