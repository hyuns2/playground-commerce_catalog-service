package io.playground.catalogservice.application.usecase;

import io.playground.catalogservice.application.dto.CatalogDto;
import io.playground.catalogservice.application.dto.ProductSearchResult;
import io.playground.catalogservice.application.port.InventoryClientPort;
import io.playground.catalogservice.application.port.ProductPersistencePort;
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
    private final CatalogTxService catalogTxService;
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

    /**
     * 특정 상품의 모든 옵션 조회 + with 재고 정보
     *
     * @param productId 상품 ID
     */
    public List<CatalogDto.VariantInfo> getVariantInfos(Long productId) {
        Map<Long, Integer> stocks = inventoryClient
                .findStockInfosByProductId(productId);

        return catalogTxService.getVariantInfos(productId, stocks);
    }
}
