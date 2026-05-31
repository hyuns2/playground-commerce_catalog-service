package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "products"
//        indexes = {
//                @Index(name = "idx_status_name", columnList = "status, name"),
//        }
)
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

    public static ProductEntity fromDomain(Product product) {
        return ProductEntity.builder()
                .status(product.getStatus())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    public Product toDomain() {
        return Product.of(
                this.id,
                this.status,
                this.name,
                this.description,
                this.detail
        );
    }
}
