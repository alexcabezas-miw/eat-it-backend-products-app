package com.upm.miw.tfm.eatitproductsapp.web;

import com.upm.miw.tfm.eatitproductsapp.exception.ValidationException;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(ProductsController.PRODUCTS_PATH)
public class ProductsController {
    public static final String PRODUCTS_PATH = "/products";
    public static final String ADD_PRODUCT_PATH = "";

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(ADD_PRODUCT_PATH)
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreationDTO dto) {
        try {
            ProductCreationOutputDTO output = this.productsService.createProduct(dto);
            return ResponseEntity
                    .created(URI.create(PRODUCTS_PATH + "/" + output.getBarcode()))
                    .build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
