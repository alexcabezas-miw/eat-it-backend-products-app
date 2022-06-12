package com.upm.miw.tfm.eatitproductsapp.service.mapper

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.web.dto.RestrictionCreationDTO
import org.springframework.beans.factory.annotation.Autowired

class RestrictionsMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RestrictionsMapper restrictionsMapper

    def "mapping to entity from creation dto works correctly" () {
        given:
        RestrictionCreationDTO creationDTO = RestrictionCreationDTO.builder().name("Veganismo").ingredients(["Leche"]).build()

        when:
        def entity = this.restrictionsMapper.fromCreationDTO(creationDTO)

        then:
        entity.getName() == creationDTO.getName()

        // We are doing this mapping in service
        entity.getIngredients().isEmpty()
    }
}
