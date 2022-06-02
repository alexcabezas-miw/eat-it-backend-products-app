package com.upm.miw.tfm.eatitproductsapp.service;

import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO;

import java.util.Collection;
import java.util.Optional;

public interface ProductsService {
    ProductCreationOutputDTO createProduct(ProductCreationDTO dto);
    Optional<ProductListDTO> findProductByBarcode(String barcode);
    Collection<ProductListDTO> findProductThatContainsName(String productName);
}
