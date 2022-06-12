package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RestrictionsMapper {

    @Mapping(target = "ingredients", ignore = true)
    Restriction fromCreationDTO(RestrictionCreationDTO dto);

    RestrictionDTO toRestrictionDTO(Restriction restriction);

    default List<String> mapIngredientList(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }
}
