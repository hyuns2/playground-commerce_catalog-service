package io.playground.catalogservice.application.port;

import io.playground.catalogservice.application.dto.ProductSearchResult;

import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {
    List<ProductSearchResult> searchAvailableProducts(String keyword, int page, int size);

    Optional<String> findDetailById(Long productId);
}
