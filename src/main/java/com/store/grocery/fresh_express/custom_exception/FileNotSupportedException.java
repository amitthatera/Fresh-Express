package com.store.grocery.fresh_express.custom_exception;

public class FileNotSupportedException extends RuntimeException{

    public FileNotSupportedException(String message){
        super(message);
    }
}
