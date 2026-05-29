package io.playground.catalogservice.infrastructure.persistence;

import io.playground.catalogservice.application.dto.ProductSearchResult;
import io.playground.catalogservice.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT new io.playground.catalogservice.application.dto.ProductSearchResult(" +
                    "p.id, p.name, MIN(v.price), p.description) " +
                "FROM ProductEntity p " +
                "JOIN VariantEntity v ON p = v.product " +
            "WHERE p.status = :status AND " +
                "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "GROUP BY p.id")
    List<ProductSearchResult> findSearchResultByStatusEqualsAndKeyword(Product.ProductStatus status,
                                                                       String keyword,
                                                                       Pageable pageable);

    @Query("SELECT p.detail FROM ProductEntity p WHERE p.id = :productId")
    Optional<String> findDetailById(Long productId);
}
