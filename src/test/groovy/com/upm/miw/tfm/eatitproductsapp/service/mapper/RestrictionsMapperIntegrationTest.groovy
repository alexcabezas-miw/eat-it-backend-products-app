package com.upm.miw.tfm.eatitproductsapp.service.mapper

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO
import org.springframework.beans.factory.annotation.Autowired

class RestrictionsMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RestrictionsMapper restrictionsMapper

    def "mapping to entity from creation dto works correctly" () {
        given:
        RestrictionCreationDTO creationDTO = RestrictionCreationDTO.builder().name("Veganismo").ingredients(["Leche"]).build()

        when:
        def entity = this.restrictionsMapper.fromCreationDTO(creationDTO)

        then:
        entity.getName() == creationDTO.getName()

        // We are doing this mapping in service
        entity.getIngredients().isEmpty()
    }

    def "mapping to restrictionDTO works correctly" () {
        given:
        Restriction restriction = Restriction.builder().name("name").ingredients([
                Ingredient.builder().name("ingredient1").build(),
                Ingredient.builder().name("ingredient2").build(),
                Ingredient.builder().name("ingredient3").build()
        ]).build()

        when:
        def dto = this.restrictionsMapper.toRestrictionDTO(restriction)

        then:
        dto.getName() == restriction.getName()
        dto.getIngredients().containsAll(["ingredient1", "ingredient2", "ingredient3"])
    }
}
