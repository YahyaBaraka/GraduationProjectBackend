package com.example.graduationproject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_providers")
@Data
@NoArgsConstructor
public class ServiceProvider {
    @Id @GeneratedValue private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String description;
    @Column(nullable = false) private String phone;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch  = FetchType.LAZY
    )
    @JoinTable(
            name                 = "service_provider_product",
            joinColumns          = @JoinColumn(name = "service_provider_id"),
            inverseJoinColumns   = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProviderType type;
    @Column(nullable = false)
    private String imageUrl;
}
