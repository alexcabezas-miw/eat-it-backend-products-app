package com.upm.miw.tfm.eatitproductsapp.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super("Validation exception: " + message);
    }
}
