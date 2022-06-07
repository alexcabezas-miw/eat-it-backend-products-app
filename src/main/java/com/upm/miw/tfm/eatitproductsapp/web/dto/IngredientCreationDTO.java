package com.upm.miw.tfm.eatitproductsapp.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class IngredientCreationDTO {
    @NotBlank
    private String name;
}
