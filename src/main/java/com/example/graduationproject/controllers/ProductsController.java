package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products/")
@Log
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createServiceProvider(@RequestBody Product product) {
        log.info("Creating product " + product);
        return ResponseEntity.ok(productService.saveProduct(product));
    }
}
