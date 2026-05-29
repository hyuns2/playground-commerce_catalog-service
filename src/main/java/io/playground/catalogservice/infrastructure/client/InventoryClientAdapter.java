package io.playground.catalogservice.infrastructure.client;

import com.fasterxml.jackson.core.type.TypeReference;
import feign.FeignException;
import io.playground.catalogservice.application.port.InventoryClientPort;
import io.playground.catalogservice.exception.BusinessDetailException;
import io.playground.catalogservice.exception.BusinessErrorCode;
import io.playground.catalogservice.infrastructure.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryClientAdapter implements InventoryClientPort {
    private final InventoryFeignClient inventoryClient;
    private final JsonUtil jsonUtil;

    @Override
    public Map<Long, Integer> findStockInfosByProductId(Long productId) {
        try {
            return jsonUtil.convert(
                    inventoryClient.getStocks(productId)
                            .getBody(),
                    new TypeReference<Map<Long, Integer>>() {}
            );
        } catch (FeignException fe) {
            if (jsonUtil.isBusinessDetailError(fe.contentUTF8()))
                throw new BusinessDetailException(
                        BusinessErrorCode.INVENTORY_SERVICE_FAILED,
                        fe.contentUTF8()
                );

            fe.printStackTrace();
            throw fe;
        }
    }
}
