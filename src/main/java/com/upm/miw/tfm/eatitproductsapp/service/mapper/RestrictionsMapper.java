package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction;
import com.upm.miw.tfm.eatitproductsapp.web.dto.RestrictionCreationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestrictionsMapper {

    @Mapping(target = "ingredients", ignore = true)
    Restriction fromCreationDTO(RestrictionCreationDTO dto);
}
