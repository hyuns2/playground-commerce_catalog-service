package io.playground.catalogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private Long id;

    private ProductStatus status;

    private String name;

    private String description;

    private String detail;

    public enum ProductStatus {
        AVAILABLE,
        UNAVAILABLE,
        DELETED
    }

    public static Product of(Long id,
                             ProductStatus status,
                             String name,
                             String description,
                             String detail) {
        return new Product(id, status, name, description, detail);
    }
}
