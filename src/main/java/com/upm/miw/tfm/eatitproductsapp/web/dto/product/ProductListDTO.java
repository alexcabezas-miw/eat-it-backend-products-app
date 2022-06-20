package com.upm.miw.tfm.eatitproductsapp.web.dto.product;

import com.upm.miw.tfm.eatitproductsapp.web.dto.comment.CommentDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductListDTO {
    private String id;
    private String barcode;
    private String name;
    private List<String> ingredients;
    private List<CommentDTO> comments;
}
