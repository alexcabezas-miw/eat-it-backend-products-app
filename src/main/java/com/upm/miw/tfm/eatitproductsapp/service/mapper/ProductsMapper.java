package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.service.model.Product;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product fromCreationDTO(ProductCreationDTO dto);
    ProductCreationOutputDTO toCreationOutputDTO(Product product);
    ProductListDTO toProductListDTO(Product product);

    default List<Ingredient> fromIngredientsNameList(List<String> source) {
        return source.stream().map(s -> Ingredient.builder().name(s).build()).collect(Collectors.toList());
    }
}
