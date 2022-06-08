package com.upm.miw.tfm.eatitproductsapp.exception;

public class IngredientDoesNotExistValidationException extends ValidationException {
    public IngredientDoesNotExistValidationException(String name) {
        super("Ingredient with name " + name + " was not found.");
    }
}
