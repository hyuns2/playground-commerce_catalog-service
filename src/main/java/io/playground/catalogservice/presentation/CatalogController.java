package io.playground.catalogservice.presentation;

import io.playground.catalogservice.application.dto.CatalogDto;
import io.playground.catalogservice.application.usecase.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping("/search")
    public ResponseEntity<List<CatalogDto.ProductSearchResult>> searchProductsByKeyword(@RequestParam String keyword,
                                                                                        @RequestParam int page) {
        return ResponseEntity.ok().body(
                catalogService
                        .searchProducts(keyword, page)
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<String> getDetail(@RequestParam Long productId) {
        return ResponseEntity.ok(
                catalogService.getDetail(productId)
        );
    }

    @GetMapping("/variant-infos")
    public ResponseEntity<List<CatalogDto.VariantInfo>> getVariantInfos(@RequestParam Long productId) {
        return ResponseEntity.ok().body(
                catalogService.getVariantInfos(productId)
        );
    }

    @GetMapping("/variants")
    public ResponseEntity<List<CatalogDto.VariantInfo>> getVariants(@RequestParam Long productId) {
        return ResponseEntity.ok().body(
                catalogService.getVariants(productId)
        );
    }
}
