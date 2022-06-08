package com.upm.miw.tfm.eatitproductsapp.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductListDTO {
    private String id;
    private String barcode;
    private String name;
    private List<String> ingredients;
}
