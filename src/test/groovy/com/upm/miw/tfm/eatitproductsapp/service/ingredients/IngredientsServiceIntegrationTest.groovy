package com.upm.miw.tfm.eatitproductsapp.service.ingredients

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.IngredientAlreadyExistsValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientCreationDTO
import org.springframework.beans.factory.annotation.Autowired

class IngredientsServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    IngredientsService ingredientsService


    def "service creates ingredient correctly if ingredient does not exist" () {
        when:
        ingredientsService.createIngredient(IngredientCreationDTO.builder().name("name").build())

        then:
        noExceptionThrown()
        ingredientsRepository.findAll().size() == 1
    }

    def "service throws error if ingredient already exists" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("name").build())

        when:
        ingredientsService.createIngredient(IngredientCreationDTO.builder().name("name").build())

        then:
        thrown(IngredientAlreadyExistsValidationException)
    }

    def "service returns ingredients when finding by search term" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("a").build())
        ingredientsRepository.save(Ingredient.builder().name("ab").build())
        ingredientsRepository.save(Ingredient.builder().name("abc").build())
        ingredientsRepository.save(Ingredient.builder().name("bc").build())
        ingredientsRepository.save(Ingredient.builder().name("c").build())

        when:
        def ingredients = ingredientsService.findIngredientsWithNameLike("a")

        then:
        ingredients.size() == 3
    }

    def "service returns empty when finding by search term didn't find any product" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("a").build())
        ingredientsRepository.save(Ingredient.builder().name("ab").build())
        ingredientsRepository.save(Ingredient.builder().name("abc").build())
        ingredientsRepository.save(Ingredient.builder().name("bc").build())
        ingredientsRepository.save(Ingredient.builder().name("c").build())

        when:
        def ingredients = ingredientsService.findIngredientsWithNameLike("d")

        then:
        ingredients.isEmpty()
    }
}
