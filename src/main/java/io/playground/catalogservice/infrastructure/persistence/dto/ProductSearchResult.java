package io.playground.catalogservice.infrastructure.persistence.dto;

import java.math.BigDecimal;

public interface ProductSearchResult {
    Long getId();

    String getName();

    BigDecimal getMinPrice();

    String getDescription();
}
