package io.playground.catalogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Variant {
    private Long id;

    private Long productId;

    private String name;

    private BigDecimal price;

    public static Variant of(Long id,
                             Long productId,
                             String name,
                             BigDecimal price) {
        return new Variant(id, productId, name, price);
    }
}
