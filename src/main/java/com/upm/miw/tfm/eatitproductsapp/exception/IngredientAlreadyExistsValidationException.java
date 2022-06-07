package com.upm.miw.tfm.eatitproductsapp.exception;

public class IngredientAlreadyExistsValidationException extends ValidationException {
    public IngredientAlreadyExistsValidationException(String message) {
        super("Ingredient with name " + message + " already exists.");
    }
}
