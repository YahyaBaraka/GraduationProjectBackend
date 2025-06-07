package com.example.graduationproject.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProductType;
import com.example.graduationproject.repositrories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void saveProductReturnsSavedProduct() {
        Product product = new Product(1L, "desc", "name", 10.0f, ProductType.UNKNOWN, 123L, "img");
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertSame(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void saveProductLogsAndRethrowsOnError() {
        Product product = new Product(1L, "desc", "name", 10.0f, ProductType.UNKNOWN, 123L, "img");
        RuntimeException ex = new RuntimeException("boom");
        when(productRepository.save(product)).thenThrow(ex);

        Logger logger = (Logger) LoggerFactory.getLogger(ProductService.class);
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
        assertSame(ex, thrown);

        boolean errorLogged = appender.list.stream().anyMatch(e ->
                e.getLevel() == Level.ERROR && e.getFormattedMessage().contains(ex.getMessage()));
        boolean infoLogged = appender.list.stream().anyMatch(e ->
                e.getLevel() == Level.INFO && e.getFormattedMessage().contains("Error in saving product"));

        logger.detachAppender(appender);
        assertTrue(errorLogged, "Error message not logged");
        assertTrue(infoLogged, "Info message not logged");
    }
}