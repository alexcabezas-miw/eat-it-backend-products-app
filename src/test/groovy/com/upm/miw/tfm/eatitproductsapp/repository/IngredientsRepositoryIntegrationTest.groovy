package com.upm.miw.tfm.eatitproductsapp.repository

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient

class IngredientsRepositoryIntegrationTest extends AbstractIntegrationTest {

    def "repository returns ingredient by name if exist" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("name").build())

        when:
        Optional<Ingredient> ingredient = ingredientsRepository.findByName("name")

        then:
        ingredient.isPresent()
    }

    def "repository returns empty by name if does not exist" () {
        when:
        Optional<Ingredient> ingredient = ingredientsRepository.findByName("name")

        then:
        ingredient.isEmpty()
    }
}
