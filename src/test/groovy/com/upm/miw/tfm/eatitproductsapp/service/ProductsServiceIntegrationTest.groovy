package com.upm.miw.tfm.eatitproductsapp.service

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.exception.BarcodeAlreadyAssignedToProductValidationException
import com.upm.miw.tfm.eatitproductsapp.exception.ProductNotFoundValidationException
import com.upm.miw.tfm.eatitproductsapp.service.model.Product
import com.upm.miw.tfm.eatitproductsapp.service.products.ProductsService
import com.upm.miw.tfm.eatitproductsapp.web.dto.comment.CommentInputDTO
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductCreationDTO
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductListDTO
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

    def "findByNameLike returns products which name fits with the search term" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Jamón de pata negra").barcode("barcode2").build())
        this.productsRepository.save(Product.builder().name("Tacos de Jamón").barcode("barcode3").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        Collection<ProductListDTO> products = this.productsService.findProductThatContainsName("Jamón")

        then:
        products.size() == 2
    }

    def "findByNameLike returns empty list when no products are found" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        Collection<ProductListDTO> products = this.productsService.findProductThatContainsName("Jamón")

        then:
        products.isEmpty()
    }

    def "remvove product by barcode removes the product if product exists" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())

        when:
        this.productsService.removeProductByBarcode("barcode1")

        then:
        this.productsRepository.findAll().isEmpty()
    }

    def "remvove product by barcode throws exception if product does not exist" () {
        when:
        this.productsService.removeProductByBarcode("barcode1")

        then:
        thrown(ProductNotFoundValidationException)
    }

    def "add comment to product add it to the ones before if product exists" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())

        when:
        this.productsService.addCommentToProduct("acabezas", CommentInputDTO.builder().barcode("barcode1").content("content").build())

        then:
        this.productsRepository.findByBarcode("barcode1").get().getComments().size() == 1
    }

    def "add comment to product throws error if product does not exists" () {
        when:
        this.productsService.addCommentToProduct("acabezas", CommentInputDTO.builder().barcode("barcode1").content("content").build())

        then:
        thrown(ProductNotFoundValidationException)
    }
}
