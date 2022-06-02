package com.upm.miw.tfm.eatitproductsapp.service;

import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.exception.BarcodeAlreadyAssignedToProductValidationException;
import com.upm.miw.tfm.eatitproductsapp.repository.ProductsRepository;
import com.upm.miw.tfm.eatitproductsapp.service.mapper.ProductsMapper;
import com.upm.miw.tfm.eatitproductsapp.service.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    public ProductsServiceImpl(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    @Override
    public ProductCreationOutputDTO createProduct(ProductCreationDTO dto) {
        Optional<Product> productByBarcode = this.productsRepository.findByBarcode(dto.getBarcode());
        if(productByBarcode.isPresent()) {
            throw new BarcodeAlreadyAssignedToProductValidationException(dto.getBarcode());
        }

        Product product = this.productsMapper.fromCreationDTO(dto);
        Product savedProduct = this.productsRepository.save(product);
        return this.productsMapper.toCreationOutputDTO(savedProduct);
    }
}
