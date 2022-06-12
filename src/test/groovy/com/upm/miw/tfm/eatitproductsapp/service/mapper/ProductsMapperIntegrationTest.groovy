package com.upm.miw.tfm.eatitproductsapp.service.mapper

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Product
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductCreationDTO
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductCreationOutputDTO
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductListDTO
import org.springframework.beans.factory.annotation.Autowired

class ProductsMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductsMapper productsMapper

    def "map to product from creation dto works correctly" () {
        given:
        ProductCreationDTO productCreationDTO = ProductCreationDTO.builder()
                .barcode("barcode")
                .name("name").build()

        when:
        Product product = this.productsMapper.fromCreationDTO(productCreationDTO)

        then:
        product.getBarcode() == productCreationDTO.getBarcode()
        product.getName() == productCreationDTO.getName()
    }

    def "map to creationOutputDto from product works correctly" () {
        given:
        Product product = Product.builder()
                .barcode("barcode")
                .name("name").build()

        when:
        ProductCreationOutputDTO outputDTO = this.productsMapper.toCreationOutputDTO(product)

        then:
        outputDTO.getBarcode() == product.getBarcode()
    }

    def "map to product list dto works correctly" () {
        given:
        Product product = Product.builder()
                .id("id")
                .barcode("barcode")
                .name("name").build()

        when:
        ProductListDTO dto = this.productsMapper.toProductListDTO(product)

        then:
        dto.getId() == product.getId()
        dto.getBarcode() == product.getBarcode()
        dto.getName() == product.getName()
    }
}
