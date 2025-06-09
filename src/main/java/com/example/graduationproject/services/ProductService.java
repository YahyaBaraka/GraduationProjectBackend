package com.example.graduationproject.services;

import com.example.graduationproject.exceptions.ProductConflictException;
import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.repositrories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id){
        return productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getAllProducts(){
        try {
            List<Product> products = productRepository.findAll();
            log.info("Fetching all products succeeded");
            return products;
        } catch (Exception exception) {
            log.error("Fetching all products failed");
            throw exception;
        }
    }


    public Product saveProduct(Product product){
        productRepository
                .findProductByBarcodeAndNameAndDescriptionAndPriceAndType(
                        product.getBarcode(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getType())
                .ifPresent(p -> {
                    throw new ProductConflictException("Product already exists");
                });
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error in saving product{}", product);
            throw e;
        }
    }

    public List<Product> getProductByBarcode(Long barcode) {
        List<Product> products = productRepository.findProductByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException(barcode));
        log.info("received products with barcode : " + barcode + " is: " + products.toString());
        return products;
    }
}
