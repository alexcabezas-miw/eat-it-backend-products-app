package com.upm.miw.tfm.eatitproductsapp.web;

import com.upm.miw.tfm.eatitproductsapp.exception.ValidationException;
import com.upm.miw.tfm.eatitproductsapp.service.ingredients.IngredientsService;
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.IngredientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(IngredientsController.INGREDIENTS_PATH)
public class IngredientsController {
    public static final String INGREDIENTS_PATH = "/ingredients";
    public static final String CREATE_INGREDIENT_PATH = "";
    public static final String FIND_BY_NAME_IN_INGREDIENT_PATH = "/{name}";

    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping(CREATE_INGREDIENT_PATH)
    @PreAuthorize("hasAnyRole('ROLE_DEFAULT_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> createIngredient(@RequestBody @Valid IngredientCreationDTO dto) {
        try {
            IngredientCreationDTO ingredient = this.ingredientsService.createIngredient(dto);
            return ResponseEntity.created(URI.create("/ingredients/" + UriUtils.encodePath(ingredient.getName(), "UTF-8"))).build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(FIND_BY_NAME_IN_INGREDIENT_PATH)
    @PreAuthorize("hasAnyRole('ROLE_DEFAULT_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Collection<IngredientDTO>> findIngredientsWithNameLike(@PathVariable("name") String name) {
        Collection<IngredientDTO> ingredients = this.ingredientsService.findIngredientsWithNameLike(name);
        return ResponseEntity.ok(ingredients);
    }
}
