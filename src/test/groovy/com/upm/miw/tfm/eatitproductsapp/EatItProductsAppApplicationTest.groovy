package com.upm.miw.tfm.eatitproductsapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class EatItProductsAppApplicationTest extends AbstractIntegrationTest {
    @Autowired
    ApplicationContext context

    def "application starts correctly" () {
        assert context != null
    }
}
