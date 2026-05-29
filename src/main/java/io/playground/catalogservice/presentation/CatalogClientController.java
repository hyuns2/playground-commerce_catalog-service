package io.playground.catalogservice.presentation;

import io.playground.catalogservice.application.dto.Snapshot;
import io.playground.catalogservice.application.usecase.CatalogClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog/client")
@RequiredArgsConstructor
public class CatalogClientController {
    private final CatalogClientService catalogClientService;

    @GetMapping
    public ResponseEntity<List<Snapshot>> getSnapshots(@RequestParam List<Long> variantIds) {
        return ResponseEntity.ok().body(
                catalogClientService.getSnapshots(variantIds)
        );
    }
}
