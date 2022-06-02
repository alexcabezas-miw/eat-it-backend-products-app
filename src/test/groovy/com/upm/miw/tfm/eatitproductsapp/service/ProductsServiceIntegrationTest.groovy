package com.upm.miw.tfm.eatitproductsapp.service

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.BarcodeAlreadyAssignedToProductValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Product
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO
import org.springframework.beans.factory.annotation.Autowired

class ProductsServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductsService productsService

    def "create product add the product to the database successfully" () {
        given:
        ProductCreationDTO product = ProductCreationDTO.builder().name("Almendras").barcode("barcode1").build()

        when:
        def result = this.productsService.createProduct(product)

        then:
        result.getBarcode() == "barcode1"
    }

    def "create a product throws exception if barcode already exists" () {
        given:
        this.productsRepository.save(Product.builder().name("product").barcode("barcode1").build())
        ProductCreationDTO product = ProductCreationDTO.builder().name("Almendras").barcode("barcode1").build()

        when:
        this.productsService.createProduct(product)

        then:
        thrown(BarcodeAlreadyAssignedToProductValidationException)
    }

    def "find product by barcode returns the product if it is found" () {
        given:
        this.productsRepository.save(Product.builder().name("product").barcode("barcode1").build())

        when:
        Optional<ProductListDTO> result = this.productsService.findProductByBarcode("barcode1")

        then:
        result.isPresent()
        result.get().getBarcode() == "barcode1"
    }

    def "find product by barcode returns empty if no product was found" () {
        when:
        Optional<ProductListDTO> result = this.productsService.findProductByBarcode("barcode1")

        then:
        result.isEmpty()
    }
}
