package io.playground.catalogservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "inventoryClient",
        url = "${api.internal.inventory-service.url}"
)
public interface InventoryFeignClient {
    @GetMapping("/stocks")
    ResponseEntity<?> getStocks(@RequestParam Long productId);
}
