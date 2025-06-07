package com.example.graduationproject.controllers;

import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProductType;
import com.example.graduationproject.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private Product sampleProduct() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Test");
        p.setDescription("Description");
        p.setPrice(10.0f);
        p.setType(ProductType.GLUTEN_FREE);
        p.setBarcode(123L);
        p.setImageUrl("url");
        return p;
    }

    @Test
    void getProductReturnsOk() throws Exception {
        Product p = sampleProduct();
        when(productService.getProductById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getProductNotFoundHandled() throws Exception {
        when(productService.getProductById(2L)).thenThrow(new ProductNotFoundException(2L));

        mockMvc.perform(get("/api/products/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not found with id: 2"));
    }

    @Test
    void createProductsHandlesError() throws Exception {
        when(productService.saveProduct(sampleProduct())).thenThrow(new RuntimeException("fail"));

        mockMvc.perform(post("/api/products/create")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("[{}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }
}