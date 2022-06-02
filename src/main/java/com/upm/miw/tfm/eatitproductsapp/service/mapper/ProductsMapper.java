package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.service.model.Product;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product fromCreationDTO(ProductCreationDTO dto);
    ProductCreationOutputDTO toCreationOutputDTO(Product product);
    ProductListDTO toProductListDTO(Product product);
}
