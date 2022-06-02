package com.upm.miw.tfm.eatitproductsapp.repository

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Product

class ProductsRepositoryIntegrationTest extends AbstractIntegrationTest {

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

    def "findByNameLike returns products which name fits with the search term" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Jamón de pata negra").barcode("barcode2").build())
        this.productsRepository.save(Product.builder().name("Tacos de Jamón").barcode("barcode3").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        Collection<Product> products = this.productsRepository.findByNameLike("Jamón")

        then:
        products.size() == 2
    }

    def "findByNameLike returns empty list when no products are found" () {
        given:
        this.productsRepository.save(Product.builder().name("Alitas de pollo").barcode("barcode1").build())
        this.productsRepository.save(Product.builder().name("Cebolla").barcode("barcode4").build())

        when:
        Collection<Product> products = this.productsRepository.findByNameLike("Jamón")

        then:
        products.isEmpty()
    }
}
