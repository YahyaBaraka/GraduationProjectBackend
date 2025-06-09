package com.example.graduationproject.controllers;

import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/products/")
@Slf4j
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.info("Received Request to get product with id: " + id);
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<Product>> getProductByBarcode(@PathVariable Long barcode) {
        try {
            log.info("Received Request to get product with barcode: " + barcode);
            return ResponseEntity.ok(productService.getProductByBarcode(barcode));
        } catch (ProductNotFoundException exception) {
            log.error(exception.toString());
            log.error("product with barcode : " + barcode + "can't be found");
            throw exception;
        } catch (Exception exception) {
            log.error("product with barcode : " + barcode + "can't be found");
            throw exception;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Received Request to get all product records");
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping("/create")
    public ResponseEntity<Product[]> createProducts(@Valid @RequestBody Product[] products) {
        log.info("Received Request to create products with values: " + Arrays.toString(products));
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            log.info("Creating product " + product);
            productList.add(productService.saveProduct(product));
        }
        log.info("Returning products: " + Arrays.toString(productList.toArray()));
        return ResponseEntity.ok(productList.toArray(Product[]::new));
    }
}
