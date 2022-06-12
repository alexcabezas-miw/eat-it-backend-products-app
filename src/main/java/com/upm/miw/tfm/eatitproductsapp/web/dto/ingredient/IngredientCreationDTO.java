package com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCreationDTO {
    @NotBlank
    private String name;
}
