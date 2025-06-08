package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "title is required")
    private String title;
    @Column(nullable = false, length = 2000)
    @NotBlank(message = "content is required")
    private String content;
}
