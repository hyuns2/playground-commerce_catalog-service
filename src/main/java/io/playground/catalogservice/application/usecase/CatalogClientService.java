package io.playground.catalogservice.application.usecase;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.application.port.VariantPersistencePort;
import io.playground.catalogservice.exception.BusinessDetailException;
import io.playground.catalogservice.exception.BusinessErrorCode;
import io.playground.catalogservice.infrastructure.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogClientService {
    private final VariantPersistencePort variantPersistence;
    private final CatalogTxService catalogTxService;
    private final JsonUtil jsonUtil;

    /**
     * 옵션별로 상품&옵션 정보 스냅샷 조회
     *
     * @param variantIds {옵션 ID} 리스트
     */
    @Transactional(readOnly = true)
    public List<Snapshot> getSnapshots(List<Long> variantIds) {
        List<Snapshot> snapshots = variantPersistence
                .findSnapshotsByIds(variantIds);

        if (variantIds.size() != snapshots.size())
            throw new BusinessDetailException(
                    BusinessErrorCode.VARIANT_NOT_FOUND,
                    jsonUtil.toJson(
                            Map.of(
                                    "variantIds",
                                    snapshots.stream()
                                            .map(Snapshot::variantId)
                                            .filter(id -> !variantIds.contains(id))
                                            .toList()
                            )
                    )
            );

        return snapshots;
    }

    /**
     * 옵션별로 상품&옵션 정보 스냅샷 조회
     *
     * @param variantIds {옵션 ID} 리스트
     */
    @Transactional(readOnly = true)
    public List<Snapshot> getHotSnapshots(List<Long> variantIds) {
        if (variantIds.size() != 1)
            throw new BusinessDetailException(
                    BusinessErrorCode.BAD_SNAPSHOT_REQUEST,
                    "NOT_SINGLE_VARIANT_ID"
            );

        return List.of(
                catalogTxService.getHotSnapshot(variantIds.get(0))
        );
    }
}
