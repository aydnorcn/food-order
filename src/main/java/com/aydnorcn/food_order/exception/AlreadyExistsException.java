package com.aydnorcn.food_order.exception;

public class AlreadyExistsException extends RuntimeException
{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
