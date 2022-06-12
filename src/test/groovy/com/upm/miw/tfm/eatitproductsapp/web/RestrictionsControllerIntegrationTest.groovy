package com.upm.miw.tfm.eatitproductsapp.web

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class RestrictionsControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RestrictionsController restrictionsController

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 204 when creation is success" () {
        given:
        this.ingredientsRepository.save(Ingredient.builder().name("Leche").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Carne").build())
        this.ingredientsRepository.save(Ingredient.builder().name("Pescado").build())
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche", "Carne", "Pescado"]).build()

        when:
        def result = this.restrictionsController.createRestriction(creationDTO)

        then:
        result.getStatusCode() == HttpStatus.CREATED
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 400 when ingredient does not exists"() {
        given:
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche", "Carne", "Pescado"]).build()

        when:
        def result = this.restrictionsController.createRestriction(creationDTO)

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 400 then restriction already exists" () {
        given:
        this.ingredientsRepository.save(Ingredient.builder().name("Leche").build())
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche"]).build()
        this.restrictionsRepository.save(Restriction.builder().name("Veganismo").ingredients([]).build())

        when:
        def result = this.restrictionsController.createRestriction(creationDTO)

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "controller throws error when user is not admin" () {
        given:
        def creationDTO = RestrictionCreationDTO.builder()
                .name("Veganismo").ingredients(["Leche"]).build()

        when:
        this.restrictionsController.createRestriction(creationDTO)

        then:
        thrown(AccessDeniedException)
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 200 and restriction details when find by name" () {
        given:
        def ingredient = Ingredient.builder().name("Leche").build()
        def ingredient2 = Ingredient.builder().name("Carne").build()
        def ingredient3 = Ingredient.builder().name("Pescado").build()
        this.ingredientsRepository.saveAll([ingredient, ingredient2, ingredient3])
        this.restrictionsRepository.save(Restriction.builder().name("veganismo")
                .ingredients([ingredient, ingredient2, ingredient3]).build())

        when:
        def response = this.restrictionsController.getRestrictionDetails("veganismo")

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().getName() == "veganismo"
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 404 when find by name and no restriction was found" () {
        when:
        def response = this.restrictionsController.getRestrictionDetails("veganismo")

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "controller throws error when find by name is called with no authentication" () {
        when:
        this.restrictionsController.getRestrictionDetails("veganismo")

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 200 and list of restrictions when listAll is called" () {
        def ingredient = Ingredient.builder().name("Leche").build()
        def ingredient2 = Ingredient.builder().name("Carne").build()
        def ingredient3 = Ingredient.builder().name("Pescado").build()
        this.ingredientsRepository.saveAll([ingredient, ingredient2, ingredient3])
        this.restrictionsRepository.save(Restriction.builder().name("veganismo")
                .ingredients([ingredient, ingredient2, ingredient3]).build())
        this.restrictionsRepository.save(Restriction.builder().name("Intolerante a la lactosa")
                .ingredients([ingredient, ingredient2, ingredient3]).build())

        when:
        def response = this.restrictionsController.listAllRestrictions()

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 2
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns no content and restrictions is removed when remove by name is called" () {
        given:
        def ingredient = Ingredient.builder().name("Leche").build()
        def ingredient2 = Ingredient.builder().name("Carne").build()
        def ingredient3 = Ingredient.builder().name("Pescado").build()
        this.ingredientsRepository.saveAll([ingredient, ingredient2, ingredient3])
        this.restrictionsRepository.save(Restriction.builder().name("veganismo")
                .ingredients([ingredient, ingredient2, ingredient3]).build())
        assert this.restrictionsRepository.findAll().size() == 1

        when:
        def result = this.restrictionsController.removeRestrictionByName("veganismo")

        then:
        result.getStatusCode() == HttpStatus.NO_CONTENT
        this.restrictionsRepository.findAll().isEmpty()
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 400 when trying to remove a non existent restriction" () {
        when:
        def result = this.restrictionsController.removeRestrictionByName("veganismo")

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "controller throws exception when default user attempts to remove a restriction" () {
        when:
        this.restrictionsController.removeRestrictionByName("veganismo")

        then:
        thrown(AccessDeniedException)
    }
}
