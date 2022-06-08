package com.upm.miw.tfm.eatitproductsapp.web

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientCreationDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class IngredientsControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    IngredientsController ingredientsController

    @WithMockUser(username = "acabezas", roles = ["DEFAULT_USER"])
    def "server returns 201 when saving an ingredient if user is authenticated and ingredient did not exist previously" () {
        when:
        def response = ingredientsController.createIngredient(IngredientCreationDTO.builder().name("name").build())

        then:
        response.getStatusCode() == HttpStatus.CREATED
    }

    @WithMockUser(username = "acabezas", roles = ["DEFAULT_USER"])
    def "server returns 400 when saving an ingredient if user is authenticated but ingredient existed previously" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("name").build())

        when:
        def response = ingredientsController.createIngredient(IngredientCreationDTO.builder().name("name").build())

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    def "server throws exception when saving an ingredient if user is not authenticated" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("name").build())

        when:
        ingredientsController.createIngredient(IngredientCreationDTO.builder().name("name").build())

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithMockUser(username = "acabezas", roles = ["DEFAULT_USER"])
    def "server returns 200 and ingredients when finding by search term" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("salmon").build())
        ingredientsRepository.save(Ingredient.builder().name("sal").build())
        ingredientsRepository.save(Ingredient.builder().name("curry").build())

        when:
        def response = ingredientsController.findIngredientsWithNameLike("sal")

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 2
    }

    @WithMockUser(username = "acabezas", roles = ["DEFAULT_USER"])
    def "server returns 200 and no ingrediets when finding by search term and no ingredients were found" () {
        given:
        ingredientsRepository.save(Ingredient.builder().name("curry").build())

        when:
        def response = ingredientsController.findIngredientsWithNameLike("sal")

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().isEmpty()
    }

    def "server throws exception when user is not authenticated" () {
        when:
        ingredientsController.findIngredientsWithNameLike("sal")

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }
}
