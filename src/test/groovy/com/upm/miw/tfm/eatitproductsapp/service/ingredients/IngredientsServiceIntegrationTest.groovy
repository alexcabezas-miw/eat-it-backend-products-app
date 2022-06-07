package com.upm.miw.tfm.eatitproductsapp.service.ingredients

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.IngredientAlreadyExistsValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientCreationDTO
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

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
}
