package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
