package com.upm.miw.tfm.eatitproductsapp.web;

import com.upm.miw.tfm.eatitproductsapp.exception.ValidationException;
import com.upm.miw.tfm.eatitproductsapp.service.restrictions.RestrictionsService;
import com.upm.miw.tfm.eatitproductsapp.web.dto.RestrictionCreationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(RestrictionsController.RESTRICTIONS_PATH)
public class RestrictionsController {

    public static final String RESTRICTIONS_PATH = "/restrictions";
    public static final String CREATE_RESTRICTION_PATH = "";

    private final RestrictionsService restrictionsService;

    public RestrictionsController(RestrictionsService restrictionsService) {
        this.restrictionsService = restrictionsService;
    }

    @PostMapping(CREATE_RESTRICTION_PATH)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createRestriction(@RequestBody @Valid RestrictionCreationDTO creationDTO) {
        try {
            this.restrictionsService.createRestriction(creationDTO);
            return ResponseEntity.created(URI.create("/restrictions")).build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
