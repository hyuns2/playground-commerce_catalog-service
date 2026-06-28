package io.playground.catalogservice.infrastructure.persistence.repository;

import io.playground.catalogservice.infrastructure.persistence.dto.ProductSearchResult;
import io.playground.catalogservice.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value= """
            SELECT p.id AS id, p.name AS name, p.min_price AS minPrice, p.description AS description
                FROM products p
            WHERE p.status = :status
                AND MATCH(p.name) AGAINST(CONCAT('+', :keyword) IN BOOLEAN MODE)
            ORDER BY p.id DESC
            LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<ProductSearchResult> findSearchResultByStatusEqualsAndKeyword(String status,
                                                                       String keyword,
                                                                       int limit,
                                                                       int offset);

    @Query("SELECT p.detail FROM ProductEntity p WHERE p.id = :productId")
    Optional<String> findDetailById(Long productId);
}
