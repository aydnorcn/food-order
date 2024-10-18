package com.aydnorcn.food_order.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public APIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
