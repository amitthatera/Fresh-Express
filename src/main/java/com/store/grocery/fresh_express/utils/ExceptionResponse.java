package com.store.grocery.fresh_express.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionResponse {

    private String timestamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String details;

}
