package io.playground.catalogservice.infrastructure.persistence.adapter;

import io.playground.catalogservice.application.dto.CatalogDto;
import io.playground.catalogservice.application.port.ProductPersistencePort;
import io.playground.catalogservice.domain.Product;
import io.playground.catalogservice.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {
    private final ProductJpaRepository productRepository;

    @Override
    public List<CatalogDto.ProductSearchResult> searchAvailableProducts(String keyword,
                                                                        int page,
                                                                        int size) {
        return productRepository.findSearchResultByStatusEqualsAndKeyword(
                Product.ProductStatus.AVAILABLE.name(),
                keyword.replace("\"", ""),
                size,
                page * size
        ).stream()
                .map(result ->
                        CatalogDto.ProductSearchResult.builder()
                                    .id(result.getId())
                                    .name(result.getName())
                                    .minPrice(result.getMinPrice())
                                    .description(result.getDescription())
                                    .build()
                ).toList();
    }

    @Override
    public Optional<String> findDetailById(Long productId) {
        return productRepository.findDetailById(productId);
    }
}
