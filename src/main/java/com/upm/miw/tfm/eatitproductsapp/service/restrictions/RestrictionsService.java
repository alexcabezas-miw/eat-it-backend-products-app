package com.upm.miw.tfm.eatitproductsapp.service.restrictions;

import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO;

public interface RestrictionsService {
    void createRestriction(RestrictionCreationDTO creationDTO);
}
