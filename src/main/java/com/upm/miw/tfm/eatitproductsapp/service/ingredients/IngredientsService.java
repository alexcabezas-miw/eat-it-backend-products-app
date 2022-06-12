package com.upm.miw.tfm.eatitproductsapp.service.ingredients;

import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientDTO;

import java.util.Collection;

public interface IngredientsService {
    IngredientCreationDTO createIngredient(IngredientCreationDTO ingredientCreationDTO);
    Collection<IngredientDTO> findIngredientsWithNameLike(String name);
}
