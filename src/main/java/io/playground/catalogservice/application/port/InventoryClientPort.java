package io.playground.catalogservice.application.port;

import java.util.Map;

public interface InventoryClientPort {
    Map<Long, Integer> findStockInfosByProductId(Long productId);
}
