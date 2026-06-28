package io.playground.catalogservice.application.port;

import io.playground.catalogservice.application.dto.CatalogDto;

import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {
    List<CatalogDto.ProductSearchResult> searchAvailableProducts(String keyword, int page, int size);

    Optional<String> findDetailById(Long productId);
}
