package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.domain.Variant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "variants")
public class VariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    public static VariantEntity fromDomain(Variant variant, ProductEntity productEntity) {
        return VariantEntity.builder()
                .product(productEntity)
                .name(variant.getName())
                .price(variant.getPrice())
                .build();
    }

    public Variant toDomain() {
        return Variant.of(
                id,
                product.getId(),
                name,
                price
        );
    }
}
