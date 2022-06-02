package com.upm.miw.tfm.eatitproductsapp.repository

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Product

class ProductsRepositoryTest extends AbstractIntegrationTest {

    def "findByBarcode returns the product which barcode matches" () {
        given:
        this.productsRepository.save(Product.builder().name("name").barcode("barcode1").build())

        when:
        Optional<Product> product = this.productsRepository.findByBarcode("barcode1")

        then:
        product.isPresent()
    }

    def "findByBarcode returns empty when no product barcode matches" () {
        given:
        this.productsRepository.save(Product.builder().name("name").barcode("barcode1").build())

        when:
        Optional<Product> product = this.productsRepository.findByBarcode("barcode2")

        then:
        product.isEmpty()
    }
}
