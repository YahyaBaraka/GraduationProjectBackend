package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message="id is required")
    private Long id;
    @Column(nullable = false)
    @NotBlank(message="description is required")
    private String description;
    @Column(nullable = false)
    @NotBlank(message="name is required")
    private String name;
    @Column(nullable = false)
    @NotNull(message="price is required")
    @Positive(message="price must be > 0")
    private float price;
    @Enumerated(EnumType.STRING)
    @NotNull(message="type is required")
    @Column(nullable = false)
    private ProductType type;
    @Column(nullable = false)
    @NotNull(message="barcode is required")
    @Positive(message="barcode must be > 0")
    private Long barcode;
    @Column(nullable = false)
    @NotBlank(message="imageUrl is required")
    @URL(message="imageUrl must be a valid URL")
    private String imageUrl;
}
