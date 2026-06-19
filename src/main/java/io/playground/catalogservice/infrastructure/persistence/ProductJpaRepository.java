package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.application.dto.ProductSearchResult;
import io.playground.catalogservice.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value= """
            SELECT p.id AS id, p.name AS name, p.min_price AS minPrice, p.description AS description
            FROM products p
            WHERE p.status = 'AVAILABLE'
              AND MATCH(p.name) AGAINST(CONCAT('+' + :keyword) IN BOOLEAN MODE)
            GROUP BY
                p.id
    """, nativeQuery = true)
    List<ProductSearchResult> findSearchResultByStatusEqualsAndKeyword(Product.ProductStatus status,
                                                                       String keyword,
                                                                       Pageable pageable);

    @Query("SELECT p.detail FROM ProductEntity p WHERE p.id = :productId")
    Optional<String> findDetailById(Long productId);
}
