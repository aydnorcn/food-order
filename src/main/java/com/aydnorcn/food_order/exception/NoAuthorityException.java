package com.aydnorcn.food_order.exception;

public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException(String message) {
        super(message);
    }
}
