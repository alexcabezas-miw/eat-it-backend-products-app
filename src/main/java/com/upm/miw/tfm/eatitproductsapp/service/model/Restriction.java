package com.upm.miw.tfm.eatitproductsapp.service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(Restriction.RESTRICTIONS_COLLECTION_NAME)
public class Restriction {
    public static final String RESTRICTIONS_COLLECTION_NAME = "restrictions";

    @Id
    private String id;

    private String name;

    @DBRef
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();
}
