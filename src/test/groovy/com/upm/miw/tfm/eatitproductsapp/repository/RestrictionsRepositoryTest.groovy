package com.upm.miw.tfm.eatitproductsapp.repository

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction

class RestrictionsRepositoryTest extends AbstractIntegrationTest {

    def "Repository returns entity if found by name" () {
        given:
        this.restrictionsRepository.save(Restriction.builder().name("Veganismo").build())

        when:
        def restriction = this.restrictionsRepository.findByName("Veganismo")

        then:
        restriction.isPresent()
    }

    def "Repository returns empty if not found by name" () {
        when:
        def restriction = this.restrictionsRepository.findByName("Veganismo")

        then:
        restriction.isEmpty()
    }
}
