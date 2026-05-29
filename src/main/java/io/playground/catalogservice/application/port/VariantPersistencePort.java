package io.playground.catalogservice.application.port;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.domain.Variant;

import java.util.List;

public interface VariantPersistencePort {
    List<Variant> findAllByProductId(Long productId);

    List<Snapshot> findSnapshotsByIds(List<Long> ids);
}
