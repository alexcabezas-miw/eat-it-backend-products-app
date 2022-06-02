package com.upm.miw.tfm.eatitproductsapp.exception;

public class BarcodeAlreadyAssignedToProductValidationException extends ValidationException {

    public BarcodeAlreadyAssignedToProductValidationException(String barcode) {
        super("The barcode " + barcode + " was already assigned to a product");
    }
}
