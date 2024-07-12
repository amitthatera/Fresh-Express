package com.store.grocery.fresh_express.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RequestResponse(
        String timestamp,
        int statusCode,
        HttpStatus status,
        String token,
        String refreshToken,
        String message
) {
}
