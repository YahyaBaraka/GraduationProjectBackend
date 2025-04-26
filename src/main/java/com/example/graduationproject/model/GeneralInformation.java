package com.example.graduationproject.model;

import jakarta.persistence.*;
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
    private String title;
    @Column(nullable = false)
    private String content;
}
