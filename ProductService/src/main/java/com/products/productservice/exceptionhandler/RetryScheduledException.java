package com.products.productservice.exceptionhandler;

public class RetryScheduledException extends RuntimeException {
    public RetryScheduledException(String message) {
        super(message);
    }
}
