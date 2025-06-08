package com.example.graduationproject.services;

import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.repositrories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return productRepository.findAll();
    }

    public Product saveProduct(Product product){
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error in saving product{}", product);
            throw e;
        }
    }

    public List<Product> getProductByBarcode(Long barcode) {
        return productRepository.findProductByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException(barcode));
    }
}
