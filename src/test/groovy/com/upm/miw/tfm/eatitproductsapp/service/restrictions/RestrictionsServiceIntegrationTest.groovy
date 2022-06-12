package com.upm.miw.tfm.eatitproductsapp.service.restrictions

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.IngredientDoesNotExistValidationException
import com.upm.miw.tfm.eatitproductsapp.exception.RestrictionAlreadyExistsValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO
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

    def "service returns restriction if found by name" () {
        given:
        def ingredient = Ingredient.builder().name("Leche").build()
        def ingredient2 = Ingredient.builder().name("Carne").build()
        def ingredient3 = Ingredient.builder().name("Pescado").build()
        this.ingredientsRepository.saveAll([ingredient, ingredient2, ingredient3])
        this.restrictionsRepository.save(Restriction.builder().name("veganismo")
                .ingredients([ingredient, ingredient2, ingredient3]).build())

        when:
        def restriction = this.restrictionsService.getRestrictionByName("veganismo")

        then:
        restriction.isPresent()
        restriction.get().getName() == "veganismo"
        restriction.get().getIngredients().containsAll(["Leche", "Carne", "Pescado"])
    }

    def "service returns empty if not found by name" () {
        when:
        def restriction = this.restrictionsService.getRestrictionByName("veganismo")

        then:
        restriction.isEmpty()
    }

    def "service returns all restrictions when findAll is called" () {
        def ingredient = Ingredient.builder().name("Leche").build()
        def ingredient2 = Ingredient.builder().name("Carne").build()
        def ingredient3 = Ingredient.builder().name("Pescado").build()
        this.ingredientsRepository.saveAll([ingredient, ingredient2, ingredient3])
        this.restrictionsRepository.save(Restriction.builder().name("veganismo")
                .ingredients([ingredient, ingredient2, ingredient3]).build())
        this.restrictionsRepository.save(Restriction.builder().name("veganismo2")
                .ingredients([ingredient, ingredient2, ingredient3]).build())
        this.restrictionsRepository.save(Restriction.builder().name("veganismo3")
                .ingredients([ingredient, ingredient2, ingredient3]).build())

        when:
        def restrictions = this.restrictionsService.getAllRestrictions()

        then:
        restrictions.size() == 3
    }
}
