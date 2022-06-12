package com.upm.miw.tfm.eatitproductsapp.exception;

public class RestrictionAlreadyExistsValidationException extends ValidationException {
    public RestrictionAlreadyExistsValidationException(String restriction) {
        super("Restriction " + restriction + " already exists.");
    }
}
