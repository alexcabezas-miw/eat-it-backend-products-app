package com.upm.miw.tfm.eatitproductsapp.repository;

import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IngredientsRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> findByName(String name);
}
