package io.playground.catalogservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode {
    // 400
    VARIANT_STOCK_MISMATCH("COMMERCE-400:001", "상품 옵션과 재고 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    // 401

    // 404
    PRODUCT_NOT_FOUND("COMMERCE-404:001", "해당하는 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_DETAIL_NOT_FOUND("COMMERCE-404:002", "해당하는 상품 설명을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    VARIANT_NOT_FOUND("COMMERCE-404:003", "해당하는 상품 옵션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 500
    JSON_PROCESSING_FAILED("COMMERCE-500:001", "JSON 직렬화 처리에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_SERVICE_FAILED("COMMERCE-500:002", "인벤토리 서비스 호출에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
