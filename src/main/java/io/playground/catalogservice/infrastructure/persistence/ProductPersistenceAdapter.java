package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.application.dto.ProductSearchResult;
import io.playground.catalogservice.application.port.ProductPersistencePort;
import io.playground.catalogservice.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {
    private final ProductJpaRepository productRepository;

    @Override
    public List<ProductSearchResult> searchAvailableProducts(String keyword, int page, int size) {
        return productRepository.findSearchResultByStatusEqualsAndKeyword(
                Product.ProductStatus.AVAILABLE,
                keyword.replace("\"", ""),
                PageRequest.of(page, size)
        );
    }

    @Override
    public Optional<String> findDetailById(Long productId) {
        return productRepository.findDetailById(productId);
    }
}
