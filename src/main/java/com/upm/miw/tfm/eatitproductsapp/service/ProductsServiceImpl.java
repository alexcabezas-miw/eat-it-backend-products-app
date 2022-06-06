package com.upm.miw.tfm.eatitproductsapp.service;

import com.upm.miw.tfm.eatitproductsapp.exception.ProductNotFoundValidationException;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductCreationOutputDTO;
import com.upm.miw.tfm.eatitproductsapp.exception.BarcodeAlreadyAssignedToProductValidationException;
import com.upm.miw.tfm.eatitproductsapp.repository.ProductsRepository;
import com.upm.miw.tfm.eatitproductsapp.service.mapper.ProductsMapper;
import com.upm.miw.tfm.eatitproductsapp.service.model.Product;
import com.upm.miw.tfm.eatitproductsapp.web.dto.ProductListDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<ProductListDTO> findProductByBarcode(String barcode) {
        Optional<Product> product = this.productsRepository.findByBarcode(barcode);
        if(product.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.productsMapper.toProductListDTO(product.get()));
    }

    @Override
    public Collection<ProductListDTO> findProductThatContainsName(String productName) {
        return this.productsRepository.findByNameLike(productName).stream()
                .map(this.productsMapper::toProductListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeProductByBarcode(String barcode) {
        Optional<Product> product = this.productsRepository.findByBarcode(barcode);
        if(product.isEmpty()) {
            throw new ProductNotFoundValidationException(barcode);
        }
        this.productsRepository.delete(product.get());
    }
}
