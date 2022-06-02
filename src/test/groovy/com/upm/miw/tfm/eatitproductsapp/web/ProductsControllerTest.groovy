package com.upm.miw.tfm.eatitproductsapp.web

import com.upm.miw.tfm.eatitproductsapp.AbstractWebIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Product
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO
import org.springframework.web.reactive.function.BodyInserters

class ProductsControllerTest extends AbstractWebIntegrationTest {

    def "server returns 201 when product is created successfully" () {
        expect:
        this.webTestClient.post()
                .uri(ProductsController.PRODUCTS_PATH + ProductsController.ADD_PRODUCT_PATH)
                .body(BodyInserters.fromValue(ProductCreationDTO.builder().name("Almendras").barcode("barcode1").build()))
                .exchange()
                .expectStatus().isCreated()
    }

    def "server returns 400 when some product mandatory fields are missing" () {
        expect:
        this.webTestClient.post()
                .uri(ProductsController.PRODUCTS_PATH + ProductsController.ADD_PRODUCT_PATH)
                .body(BodyInserters.fromValue(ProductCreationDTO.builder().barcode("barcode1").build()))
                .exchange()
                .expectStatus().isBadRequest()
    }

    def "server returns 400 when attempting to add an already created product" () {
        given:
        this.productsRepository.save(Product.builder().name("name").barcode("barcode1").build())
        expect:
        this.webTestClient.post()
                .uri(ProductsController.PRODUCTS_PATH + ProductsController.ADD_PRODUCT_PATH)
                .body(BodyInserters.fromValue(ProductCreationDTO.builder().name("name").barcode("barcode1").build()))
                .exchange()
                .expectStatus().isBadRequest()
    }
}
