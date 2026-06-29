package io.playground.catalogservice.application.port;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.domain.Variant;

import java.util.List;
import java.util.Optional;

public interface VariantPersistencePort {
    List<Variant> findAllByProductId(Long productId);

    Optional<Snapshot> findSnapshotById(Long id);

    List<Snapshot> findSnapshotsByIds(List<Long> ids);
}
