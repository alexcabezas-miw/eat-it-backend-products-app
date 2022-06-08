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

    def "repository returns ingredients when finding by search term" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("a").build())
        ingredientsRepository.save(Ingredient.builder().name("ab").build())
        ingredientsRepository.save(Ingredient.builder().name("abc").build())
        ingredientsRepository.save(Ingredient.builder().name("bc").build())
        ingredientsRepository.save(Ingredient.builder().name("c").build())

        when:
        def ingredients = ingredientsRepository.findByNameLike("a")

        then:
        ingredients.size() == 3
    }

    def "repository returns empty when no ingredients were found by search term" () {
        when:
        def ingredients = ingredientsRepository.findByNameLike("a")

        then:
        ingredients.isEmpty()
    }
}
