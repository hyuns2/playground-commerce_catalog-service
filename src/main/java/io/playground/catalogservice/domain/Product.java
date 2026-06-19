package io.playground.catalogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Product {
    private Long id;

    private ProductStatus status;

    private String name;

    private String description;

    private String detail;

    private BigDecimal minPrice;

    public enum ProductStatus {
        AVAILABLE,
        UNAVAILABLE,
        DELETED
    }

    public static Product of(Long id,
                             ProductStatus status,
                             String name,
                             String description,
                             String detail,
                             BigDecimal minPrice) {
        return new Product(id, status, name, description, detail, minPrice);
    }
}
