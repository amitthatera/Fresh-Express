package com.store.grocery.fresh_express.custom_exception;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}
