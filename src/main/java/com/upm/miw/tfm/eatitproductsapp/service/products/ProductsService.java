package com.upm.miw.tfm.eatitproductsapp.service.products;

import com.upm.miw.tfm.eatitproductsapp.web.dto.comment.CommentInputDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.product.ProductListDTO;

import java.util.Collection;
import java.util.Optional;

public interface ProductsService {
    ProductCreationOutputDTO createProduct(ProductCreationDTO dto);
    Optional<ProductListDTO> findProductByBarcode(String barcode);
    Collection<ProductListDTO> findProductThatContainsName(String productName);
    void removeProductByBarcode(String barcode);
    void addCommentToProduct(String username, CommentInputDTO commentInputDTO);
}
