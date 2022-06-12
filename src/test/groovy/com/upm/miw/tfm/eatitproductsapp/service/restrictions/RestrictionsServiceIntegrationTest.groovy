package com.upm.miw.tfm.eatitproductsapp.service.restrictions

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.IngredientDoesNotExistValidationException
import com.upm.miw.tfm.eatitproductsapp.exception.RestrictionAlreadyExistsValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.web.dto.RestrictionCreationDTO
import org.springframework.beans.factory.annotation.Autowired

class RestrictionsServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RestrictionsService restrictionsService

    def "service adds restriction successfully" () {
        given:
        this.ingredientsRepository.save(Ingredient.builder().name("Leche").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Carne").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Pescado").build())
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche", "Carne", "Pescado"]).build()

        when:
        this.restrictionsService.createRestriction(creationDTO)

        then:
        this.restrictionsRepository.findAll().size() == 1
    }

    def "service throws validation exception if ingredient does not exists" () {
        given:
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche", "Carne", "Pescado"]).build()

        when:
        this.restrictionsService.createRestriction(creationDTO)

        then:
        thrown(IngredientDoesNotExistValidationException)
    }

    def "service throws error if restriction already exists" () {
        given:
        this.ingredientsRepository.save(Ingredient.builder().name("Leche").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Carne").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Pescado").build())
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche", "Carne", "Pescado"]).build()
        this.restrictionsService.createRestriction(creationDTO)

        when:
        this.restrictionsService.createRestriction(creationDTO)

        then:
        thrown(RestrictionAlreadyExistsValidationException)
    }
}
