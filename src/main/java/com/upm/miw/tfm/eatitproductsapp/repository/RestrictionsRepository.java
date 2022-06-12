package com.upm.miw.tfm.eatitproductsapp.repository;

import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestrictionsRepository extends MongoRepository<Restriction, String> {
    Optional<Restriction> findByName(String name);
}
