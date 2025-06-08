package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_provider_location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    @NotNull(message="latitude is required")
    @Positive(message="latitude must be > 0")
    private float latitude;
    @Column(nullable = false)
    @NotNull(message="longitude is required")
    @Positive(message="longitude must be > 0")
    private float longitude;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotNull(message="address is required")
    private Address address;
}
