package com.upm.miw.tfm.eatitproductsapp.web

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
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
}
