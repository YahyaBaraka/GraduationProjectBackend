package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "title is required")
    private String title;
    @Column(nullable = false, length = 2000)
    @NotBlank(message = "content is required")
    private String content;
    @Column(nullable = false)
    @NotBlank(message = "imageUrl is required")
    private String imageUrl;
}
