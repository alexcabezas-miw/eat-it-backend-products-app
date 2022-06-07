package com.upm.miw.tfm.eatitproductsapp.service.ingredients;

import com.upm.miw.tfm.eatitproductsapp.exception.IngredientAlreadyExistsValidationException;
import com.upm.miw.tfm.eatitproductsapp.repository.IngredientsRepository;
import com.upm.miw.tfm.eatitproductsapp.service.mapper.IngredientMapper;
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientCreationDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
