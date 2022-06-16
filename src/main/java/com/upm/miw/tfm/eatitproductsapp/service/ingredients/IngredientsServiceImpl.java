package com.upm.miw.tfm.eatitproductsapp.service.ingredients;

import com.upm.miw.tfm.eatitproductsapp.exception.IngredientAlreadyExistsValidationException;
import com.upm.miw.tfm.eatitproductsapp.exception.IngredientDoesNotExistValidationException;
import com.upm.miw.tfm.eatitproductsapp.repository.IngredientsRepository;
import com.upm.miw.tfm.eatitproductsapp.service.mapper.IngredientMapper;
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientMapper ingredientMapper;
    private final IngredientsRepository ingredientsRepository;

    public IngredientsServiceImpl(IngredientMapper ingredientMapper,
                                  IngredientsRepository ingredientsRepository) {
        this.ingredientMapper = ingredientMapper;
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public IngredientCreationDTO createIngredient(IngredientCreationDTO ingredientCreationDTO) {
        Optional<Ingredient> ingredientFromDB = this.ingredientsRepository.findByName(ingredientCreationDTO.getName());
        if(ingredientFromDB.isPresent()) {
            throw new IngredientAlreadyExistsValidationException(ingredientCreationDTO.getName());
        }
        Ingredient savedIngredient = this.ingredientsRepository.save(this.ingredientMapper.fromCreationDTO(ingredientCreationDTO));
        return this.ingredientMapper.toCreationDTO(savedIngredient);
    }

    @Override
    public Collection<IngredientDTO> findIngredientsWithNameLike(String name) {
        Collection<Ingredient> ingredients = this.ingredientsRepository.findByNameLike(name);
        return ingredients.stream()
                .map(ing -> IngredientDTO.builder().name(ing.getName()).build())
                .collect(Collectors.toList());
    }

    @Override
    public void removeIngredientByName(String name) {
        Optional<Ingredient> ingredient = this.ingredientsRepository.findByName(name);
        if(ingredient.isEmpty()) {
            throw new IngredientDoesNotExistValidationException(name);
        }
        this.ingredientsRepository.delete(ingredient.get());
    }
}
