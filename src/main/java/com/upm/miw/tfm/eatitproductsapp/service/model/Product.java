package com.upm.miw.tfm.eatitproductsapp.service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(Product.DOCUMENT_PRODUCT_NAME)
public class Product {

    public final static String DOCUMENT_PRODUCT_NAME = "products";

    @Id
    private String id;

    private String barcode;

    private String name;
}