package io.playground.catalogservice.infrastructure.persistence.repository;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.infrastructure.persistence.entity.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariantJpaRepository extends JpaRepository<VariantEntity, Long> {
    List<VariantEntity> findAllByProductId(Long productId);

    @Query("SELECT new io.playground.catalogservice.application.dto.Snapshot(" +
                "p.id, p.name, v.id, v.name, v.price) " +
            "FROM VariantEntity v " +
            "JOIN v.product p " +
            "WHERE v.id IN :variantIds")
    List<Snapshot> findSnapshotsByIds(List<Long> variantIds);
}
