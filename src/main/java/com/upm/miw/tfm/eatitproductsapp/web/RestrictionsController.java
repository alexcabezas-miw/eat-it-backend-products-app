package com.upm.miw.tfm.eatitproductsapp.web;

import com.upm.miw.tfm.eatitproductsapp.exception.ValidationException;
import com.upm.miw.tfm.eatitproductsapp.service.restrictions.RestrictionsService;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(RestrictionsController.RESTRICTIONS_PATH)
public class RestrictionsController {

    public static final String RESTRICTIONS_PATH = "/restrictions";
    public static final String CREATE_RESTRICTION_PATH = "";
    public static final String GET_RESTRICTION_DETAILS = "{name}";

    private final RestrictionsService restrictionsService;

    public RestrictionsController(RestrictionsService restrictionsService) {
        this.restrictionsService = restrictionsService;
    }

    @PostMapping(CREATE_RESTRICTION_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createRestriction(@RequestBody @Valid RestrictionCreationDTO creationDTO) {
        try {
            this.restrictionsService.createRestriction(creationDTO);
            return ResponseEntity.created(URI.create("/restrictions")).build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(GET_RESTRICTION_DETAILS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RestrictionDTO> getRestrictionDetails(@PathVariable("name") String name) {
        return ResponseEntity.of(this.restrictionsService.getRestrictionByName(name));
    }
}
