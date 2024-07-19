package com.store.grocery.fresh_express.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        String timestamp,
        int statusCode,
        HttpStatus status,
        String message,
        String details
) {
}
