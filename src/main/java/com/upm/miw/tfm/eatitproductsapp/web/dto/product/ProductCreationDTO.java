package com.upm.miw.tfm.eatitproductsapp.web.dto.product;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class ProductCreationDTO {

    @NotBlank
    private String barcode;

    @NotBlank
    private String name;

    @Builder.Default
    private List<String> ingredients = new ArrayList<>();

    private String image;
}
