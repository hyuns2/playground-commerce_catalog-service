package io.playground.catalogservice.infrastructure.persistence.adapter;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.application.port.VariantPersistencePort;
import io.playground.catalogservice.domain.Variant;
import io.playground.catalogservice.infrastructure.persistence.entity.VariantEntity;
import io.playground.catalogservice.infrastructure.persistence.repository.VariantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VariantPersistenceAdapter implements VariantPersistencePort {
    private final VariantJpaRepository variantRepository;

    @Override
    public List<Variant> findAllByProductId(Long productId) {
        return variantRepository
                .findAllByProductId(productId).stream()
                .map(VariantEntity::toDomain)
                .toList();
    }

    @Override
    public List<Snapshot> findSnapshotsByIds(List<Long> ids) {
        return variantRepository
                .findSnapshotsByIds(ids);
    }
}
