package com.upm.miw.tfm.eatitproductsapp.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class ProductCreationDTO {

    @NotBlank
    private String barcode;

    @NotBlank
    private String name;
}
