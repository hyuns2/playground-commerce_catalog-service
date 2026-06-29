package io.playground.catalogservice.application.usecase;

import io.playground.catalogservice.application.dto.CatalogDto;
import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.application.port.VariantPersistencePort;
import io.playground.catalogservice.domain.Variant;
import io.playground.catalogservice.exception.BusinessDetailException;
import io.playground.catalogservice.exception.BusinessErrorCode;
import io.playground.catalogservice.infrastructure.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogTxService {
    private final VariantPersistencePort variantPersistence;
    private final JsonUtil jsonUtil;

    @Transactional(readOnly = true)
    public List<CatalogDto.VariantInfo> getVariantInfos(Long productId,
                                                        Map<Long, Integer> stocks) {
        List<Variant> variants = variantPersistence
                .findAllByProductId(productId);

        if (variants.isEmpty())
            throw new BusinessDetailException(
                    BusinessErrorCode.PRODUCT_NOT_FOUND,
                    jsonUtil.toJson(
                            Map.of("productId", productId)
                    )
            );

        if (variants.size() != stocks.size())
            throw new BusinessDetailException(
                    BusinessErrorCode.VARIANT_STOCK_MISMATCH,
                    jsonUtil.toJson(
                            Map.of(
                                    "variantIds",
                                    variants.stream()
                                            .filter(v -> !stocks.containsKey(v.getId()))
                                            .toList()
                            )
                    )
            );

        return variants.stream()
                .map(v ->
                        CatalogDto.VariantInfo.from(
                                v, stocks.get(v.getId())
                        )
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<Variant> getVariants(Long productId) {
        List<Variant> variants = variantPersistence
                .findAllByProductId(productId);

        if (variants.isEmpty())
            throw new BusinessDetailException(
                    BusinessErrorCode.PRODUCT_NOT_FOUND,
                    jsonUtil.toJson(
                            Map.of("productId", productId)
                    )
            );

        return variants;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "catalog-client", key = "'variant:' + #variantId + ':snapshot:'")
    public Snapshot getHotSnapshot(Long variantId) {
        return variantPersistence.findSnapshotById(variantId)
                .orElseThrow(() -> new BusinessDetailException(
                        BusinessErrorCode.VARIANT_NOT_FOUND,
                        jsonUtil.toJson(
                                Map.of("variantId", variantId)
                        )
                ));
    }
}
