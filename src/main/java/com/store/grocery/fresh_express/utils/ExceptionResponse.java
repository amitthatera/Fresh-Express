package com.store.grocery.fresh_express.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private String details;

}
