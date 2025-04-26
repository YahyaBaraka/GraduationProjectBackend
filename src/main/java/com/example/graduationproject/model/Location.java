package com.example.graduationproject.model;

import jakarta.persistence.*;
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
    private float latitude;
    private float longitude;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
