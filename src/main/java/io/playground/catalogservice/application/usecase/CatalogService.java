package io.playground.catalogservice.application.usecase;

import io.playground.catalogservice.application.dto.CatalogDto;
import io.playground.catalogservice.application.dto.ProductSearchResult;
import io.playground.catalogservice.application.port.InventoryClientPort;
import io.playground.catalogservice.application.port.ProductPersistencePort;
import io.playground.catalogservice.application.port.VariantPersistencePort;
import io.playground.catalogservice.domain.Variant;
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
public class CatalogService {
    private final ProductPersistencePort productPersistence;
    private final VariantPersistencePort variantPersistence;
    private final InventoryClientPort inventoryClient;
    private final JsonUtil jsonUtil;
    private final static int PAGE_SIZE = 20;

    /**
     * 상품 검색
     *  - 페이지당 20개씩 조회
     *  - 페이지 번호는 0부터 시작
     *
     * @param keyword 검색어
     * @param page 페이지 번호
     */
    @Transactional(readOnly = true)
    public List<ProductSearchResult> searchProducts(String keyword,
                                                    int page) {
        return productPersistence
                .searchAvailableProducts(
                        keyword, page, PAGE_SIZE
                );
    }

    /**
     * 특정 상품의 모든 옵션 조회
     *
     * @param productId 상품 ID
     */
    @Transactional(readOnly = true)
    public List<CatalogDto.VariantInfo> getVariants(Long productId) {
        Map<Long, Integer> stocks = inventoryClient
                .findStockInfosByProductId(productId);
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

    /**
     * 특정 상품의 상세페이지 조회
     *
     * @param productId 상품 ID
     */
    @Transactional(readOnly = true)
    public String getDetail(Long productId) {
        return productPersistence.findDetailById(productId)
                .orElseThrow(() -> new BusinessDetailException(
                        BusinessErrorCode.PRODUCT_DETAIL_NOT_FOUND,
                        jsonUtil.toJson(
                                Map.of("productId", productId)
                        )
                ));
    }
}
