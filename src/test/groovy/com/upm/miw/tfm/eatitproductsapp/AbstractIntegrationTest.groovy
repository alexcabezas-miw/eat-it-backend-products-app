package com.upm.miw.tfm.eatitproductsapp

import com.upm.miw.tfm.eatitproductsapp.repository.CommentRepository
import com.upm.miw.tfm.eatitproductsapp.repository.IngredientsRepository
import com.upm.miw.tfm.eatitproductsapp.repository.ProductsRepository
import com.upm.miw.tfm.eatitproductsapp.repository.RestrictionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AbstractIntegrationTest extends Specification {

    @Autowired
    protected ProductsRepository productsRepository

    @Autowired
    protected IngredientsRepository ingredientsRepository

    @Autowired
    protected RestrictionsRepository restrictionsRepository

    @Autowired
    protected CommentRepository commentRepository

    def cleanup() {
        productsRepository.deleteAll()
        ingredientsRepository.deleteAll()
        restrictionsRepository.deleteAll()
        commentRepository.deleteAll()
    }
}
