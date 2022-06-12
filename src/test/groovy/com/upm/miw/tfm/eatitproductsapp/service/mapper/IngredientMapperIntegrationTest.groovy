package com.upm.miw.tfm.eatitproductsapp.service.mapper

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.web.dto.ingredient.IngredientCreationDTO
import org.springframework.beans.factory.annotation.Autowired

class IngredientMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    IngredientMapper ingredientMapper

    def "mapper to creation dto works correctly" () {
        given:
        Ingredient ingredient = Ingredient.builder().name("ingredient").build()

        when:
        IngredientCreationDTO dto = ingredientMapper.toCreationDTO(ingredient)

        then:
        dto.getName() == ingredient.getName()
    }

    def "mapper from creation dto works correctly" () {
        given:
        IngredientCreationDTO dto = IngredientCreationDTO.builder().name("name").build()

        when:
        Ingredient ingredient = ingredientMapper.fromCreationDTO(dto)

        then:
        ingredient.getName() == dto.getName()
    }
}
