package com.example.graduationproject.services;

import com.example.graduationproject.exceptions.ProductNotFoundException;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProductType;
import com.example.graduationproject.repositrories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Test");
        sampleProduct.setDescription("Description");
        sampleProduct.setPrice(10.0f);
        sampleProduct.setType(ProductType.GLUTEN_FREE);
        sampleProduct.setBarcode(123L);
        sampleProduct.setImageUrl("url");
    }

    @Test
    void getProductByIdReturnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(sampleProduct, result);
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductByIdThrowsWhenMissing() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(2L));
        verify(productRepository).findById(2L);
    }

    @Test
    void getAllProductsReturnsList() {
        List<Product> list = Arrays.asList(sampleProduct);
        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productService.getAllProducts();

        assertEquals(list, result);
        verify(productRepository).findAll();
    }

    @Test
    void saveProductDelegatesToRepository() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);

        Product result = productService.saveProduct(sampleProduct);

        assertEquals(sampleProduct, result);
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void saveProductPropagatesException() {
        RuntimeException ex = new RuntimeException("fail");
        when(productRepository.save(sampleProduct)).thenThrow(ex);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productService.saveProduct(sampleProduct));
        assertEquals(ex, thrown);
        verify(productRepository).save(sampleProduct);
    }
}