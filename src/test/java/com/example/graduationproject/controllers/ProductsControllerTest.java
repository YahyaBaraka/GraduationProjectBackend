package com.example.graduationproject.controllers;

import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProductType;
import com.example.graduationproject.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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
        p.setImageUrl("http://example.com/img.png");
        return p;
    }

    @Test
    void getProductReturnsOk() throws Exception {
        when(productService.getProductById(1L)).thenReturn(sampleProduct());

        mockMvc.perform(get("/api/products/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getProductNotFoundHandled() throws Exception {
        when(productService.getProductById(2L))
                .thenThrow(new ProductNotFoundException(2L));

        mockMvc.perform(get("/api/products/id/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not found with id: 2"));
    }

    @Test
    void createProductsValidationFails() throws Exception {
        // [{}] has none of the @NotNull/@NotBlank fields => 400 Bad Request
        mockMvc.perform(post("/api/products/create")
                        .contentType(APPLICATION_JSON)
                        .content("[{}]"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void createProductsServiceError() throws Exception {
        // stub any Product to throw in the service layer
        when(productService.saveProduct(any(Product.class)))
                .thenThrow(new RuntimeException("fail"));

        // serialize a valid product payload
        String validJson = mapper.writeValueAsString(new Product[]{ sampleProduct() });

        mockMvc.perform(post("/api/products/create")
                        .contentType(APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }
}
