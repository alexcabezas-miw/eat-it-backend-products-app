package com.upm.miw.tfm.eatitproductsapp.exception;

public class RestrictionDoesNotExistsValidationException extends ValidationException {
    public RestrictionDoesNotExistsValidationException(String name) {
        super("Restriction with name " + name + " does not exists.");
    }
}
