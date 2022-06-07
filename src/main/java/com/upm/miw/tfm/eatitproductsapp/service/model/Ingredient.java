package com.upm.miw.tfm.eatitproductsapp.service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(Ingredient.INGREDIENTS_COLLECTION_NAME)
public class Ingredient {
    public static final String INGREDIENTS_COLLECTION_NAME = "ingredients";

    @Id
    private String id;

    private String name;
}
