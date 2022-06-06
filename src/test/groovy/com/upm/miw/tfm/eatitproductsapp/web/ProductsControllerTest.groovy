package com.upm.miw.tfm.eatitproductsapp.web

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Product
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class ProductsControllerTest extends AbstractIntegrationTest {

    @Autowired
    ProductsController productsController


    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 201 when product is created successfully and user is admin" () {
        when:
        def response = productsController.createProduct(ProductCreationDTO.builder().name("Almendras").barcode("barcode1").build())

        then:
        response.getStatusCode() == HttpStatus.CREATED
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "server returns 400 when attempting to add an already created product and user is admin" () {
        given:
        this.productsRepository.save(Product.builder().name("name").barcode("barcode1").build())
        when:
        def response = productsController.createProduct(ProductCreationDTO.builder().name("name").barcode("barcode1").build())

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server throws error when when default user attemps to create a products" () {
        when:
        productsController.createProduct(ProductCreationDTO.builder().name("Almendras").barcode("barcode1").build())

        then:
        thrown(AccessDeniedException)
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 200 and product if it was found by barcode" () {
        given:
        this.productsRepository.save(Product.builder().name("name").barcode("barcode1").build())

        when:
        def result = productsController.getProductByBarcode("barcode1")

        then:
        result.getStatusCode() == HttpStatus.OK
        result.getBody().getName() == "name"
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 404 if it was not found by barcode" () {
        when:
        def result = productsController.getProductByBarcode("barcode1")

        then:
        result.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "server throws error if user is not authenticated" () {
        when:
        productsController.getProductByBarcode("barcode1")

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "server returns 200 and products which name fits with the search term" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Jamón de pata negra").barcode("barcode2").build())
        this.productsRepository.save(Product.builder().name("Tacos de Jamón").barcode("barcode3").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        def result = productsController.findProductsThatContainsName("Jamón")

        then:
        result.getStatusCode() == HttpStatus.OK
        result.getBody().size() == 2
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "findByNameLike returns empty list when no products are found" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        def result = productsController.findProductsThatContainsName("Jamón")

        then:
        result.getStatusCode() == HttpStatus.OK
        result.getBody().isEmpty()
    }

    def "findByNameLike throws error when user is not authenticated" () {
        when:
        productsController.findProductsThatContainsName("Jamón")

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "remove product by barcode removes the product and return 204 if product exists and user is admin" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())

        when:
        def result = productsController.removeProductByBarcode("barcode1")

        then:
        result.getStatusCode() == HttpStatus.NO_CONTENT
        productsRepository.findAll().isEmpty()
    }

    @WithMockUser(username = "admin", roles = ["ADMIN"])
    def "remove product by barcode returns 400 if product does not exists and user is admin" () {
        when:
        def result = productsController.removeProductByBarcode("barcode1")

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    @WithMockUser(username = "user", roles = ["DEFAULT_USER"])
    def "remove product by barcode throws exception if user is not admin" () {
        when:
        productsController.removeProductByBarcode("barcode1")

        then:
        thrown(AccessDeniedException)
    }
}
