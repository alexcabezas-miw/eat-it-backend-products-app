package com.upm.miw.tfm.eatitproductsapp.web;

import com.upm.miw.tfm.eatitproductsapp.exception.ValidationException;
import com.upm.miw.tfm.eatitproductsapp.service.ProductsService;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(ProductsController.PRODUCTS_PATH)
public class ProductsController {
    public static final String PRODUCTS_PATH = "/products";
    public static final String ADD_PRODUCT_PATH = "";
    public static final String FIND_BY_BARCODE_PATH = "/barcode/";
    public static final String FIND_BY_NAME_PATH = "/name/";

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(ADD_PRODUCT_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping(FIND_BY_BARCODE_PATH + "{barcode}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductListDTO> getProductByBarcode(@PathVariable("barcode") String barcode) {
        return ResponseEntity.of(this.productsService.findProductByBarcode(barcode));
    }

    @GetMapping(FIND_BY_NAME_PATH + "{productName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Collection<ProductListDTO>> findProductsThatContainsName(@PathVariable("productName") String productName) {
        return ResponseEntity.ok()
                .body(this.productsService.findProductThatContainsName(productName));
    }
}
