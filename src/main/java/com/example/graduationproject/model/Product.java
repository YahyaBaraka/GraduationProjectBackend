package com.example.graduationproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;
    @Column(nullable = false)
    private Long barcode;
    @Column(nullable = false)
    private String imageUrl;
}
