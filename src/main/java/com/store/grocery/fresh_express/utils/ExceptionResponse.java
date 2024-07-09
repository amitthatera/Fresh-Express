package com.store.grocery.fresh_express.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private String details;

}
