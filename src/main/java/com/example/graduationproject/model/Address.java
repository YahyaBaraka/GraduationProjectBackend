package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "addressId")
    Long id;
    @Column
    @NotBlank(message = "city is required")
    String city;
    @Column
    @NotBlank(message = "country is required")
    String country;
    @Column
    @NotBlank(message = "street is required")
    String street;
}
