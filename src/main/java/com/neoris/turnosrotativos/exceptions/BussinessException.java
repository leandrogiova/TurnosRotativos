package com.neoris.turnosrotativos.exceptions;

import org.springframework.http.HttpStatus;

public class BussinessException extends RuntimeException {

    HttpStatus httpStatus;

    public BussinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}