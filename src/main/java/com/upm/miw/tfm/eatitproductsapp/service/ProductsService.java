package com.upm.miw.tfm.eatitproductsapp.service;

import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;

public interface ProductsService {
    ProductCreationOutputDTO createProduct(ProductCreationDTO dto);
}
