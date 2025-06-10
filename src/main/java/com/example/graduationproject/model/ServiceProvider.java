package com.example.graduationproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false)
    @NotBlank(message = "name is required")
    private String name;
    @Column(nullable = false, length = 2000)
    @NotBlank(message = "description is required")
    private String description;
    @Column(nullable = false)
    @NotBlank(message = "phone is required")
    @Pattern(
            regexp = "^(?:\\+962|0)7\\d{8}$",
            message = "phone must be a valid Jordanian mobile number"
    )
    private String phone;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinTable(
            name                 = "service_provider_product",
            joinColumns          = @JoinColumn(name = "service_provider_id"),
            inverseJoinColumns   = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @OneToOne()
    @JoinColumn(name = "location_id")
    @NotNull(message = "location is required")
    private Location location;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "type is required")
    private ProviderType type;
    @Column(nullable = false)
    @NotBlank(message = "imageUrl is required")
    private String imageUrl;
}
