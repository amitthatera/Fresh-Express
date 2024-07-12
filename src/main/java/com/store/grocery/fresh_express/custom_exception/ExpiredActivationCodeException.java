package com.store.grocery.fresh_express.custom_exception;

public class ExpiredActivationCodeException extends RuntimeException{

    public ExpiredActivationCodeException(String message){
        super(message);
    }
}
