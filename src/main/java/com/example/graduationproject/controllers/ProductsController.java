package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Product;
import com.example.graduationproject.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/products/")
@Log
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<Product>> getProductByBarcode(@PathVariable Long barcode) {
        return ResponseEntity.ok(productService.getProductByBarcode(barcode));
    }
    @PostMapping("/create")
    public ResponseEntity<Product[]> createServiceProvider(@Valid @RequestBody Product[] products) {
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            log.info("Creating product " + product);
            productList.add(productService.saveProduct(product));
        }
        log.info("Returning products: " + Arrays.toString(productList.toArray()));
        return ResponseEntity.ok(productList.toArray(Product[]::new));
    }
}
