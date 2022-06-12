package com.upm.miw.tfm.eatitproductsapp.service.restrictions;

import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionDTO;

import java.util.Collection;
import java.util.Optional;

public interface RestrictionsService {
    void createRestriction(RestrictionCreationDTO creationDTO);
    Optional<RestrictionDTO> getRestrictionByName(String name);
    Collection<RestrictionDTO> getAllRestrictions();
    void removeRestrictionByName(String name);
}
