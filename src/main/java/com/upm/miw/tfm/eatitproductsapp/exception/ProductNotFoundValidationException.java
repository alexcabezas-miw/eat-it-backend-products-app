package com.upm.miw.tfm.eatitproductsapp.exception;

public class ProductNotFoundValidationException extends ValidationException {
    public ProductNotFoundValidationException(String barcode) {
        super("Product with barcode " + barcode + " was not found");
    }
}
