package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Product.ProductStatus status;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String detail;

    @Column
    private BigDecimal minPrice;

    public static ProductEntity fromDomain(Product product) {
        return ProductEntity.builder()
                .status(product.getStatus())
                .name(product.getName())
                .description(product.getDescription())
                .detail(product.getDetail())
                .minPrice(product.getMinPrice())
                .build();
    }

    public Product toDomain() {
        return Product.of(
                this.id,
                this.status,
                this.name,
                this.description,
                this.detail,
                this.minPrice
        );
    }
}
