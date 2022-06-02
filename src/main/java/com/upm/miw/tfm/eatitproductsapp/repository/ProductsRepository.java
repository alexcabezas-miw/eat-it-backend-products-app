package com.upm.miw.tfm.eatitproductsapp.repository;

import com.upm.miw.tfm.eatitproductsapp.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductsRepository extends MongoRepository<Product, String> {
    Optional<Product> findByBarcode(String barcode);
}
