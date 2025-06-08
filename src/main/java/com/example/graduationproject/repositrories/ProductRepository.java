package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByBarcodeAndNameAndDescriptionAndPriceAndType(Long barcode, String name, String description, float price, ProductType type);
    Optional<List<Product>> findProductByBarcode(Long barcode);
}
