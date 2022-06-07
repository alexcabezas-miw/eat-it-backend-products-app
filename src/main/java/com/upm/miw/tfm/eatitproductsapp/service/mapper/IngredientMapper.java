package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientCreationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient fromCreationDTO(IngredientCreationDTO dto);
    IngredientCreationDTO toCreationDTO(Ingredient ingredient);
}
