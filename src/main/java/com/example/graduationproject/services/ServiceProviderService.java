package com.example.graduationproject.services;

import com.example.graduationproject.exceptions.ServiceProviderConflictException;
import com.example.graduationproject.model.Location;
import com.example.graduationproject.model.Product;
import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.repositrories.LocationRepository;
import com.example.graduationproject.repositrories.ProductRepository;
import com.example.graduationproject.repositrories.ServiceProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ServiceProviderService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public ServiceProviderService(ServiceProviderRepository serviceProviderRepository, ProductRepository productRepository, LocationRepository locationRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    public List<ServiceProvider> getAllProviders() {
        try {
            return serviceProviderRepository.findAll();
        } catch (Exception exception) {
            log.error("Fetching all service providers failed");
            throw exception;
        }
    }
    public List<ServiceProvider> getProvidersByType(ProviderType providerType) {
        return serviceProviderRepository.getServiceProviderByType(providerType);
    }

    public ServiceProvider addServiceProvider(ServiceProvider serviceProvider) {
        serviceProviderRepository
                .findServiceProviderByNameAndDescriptionAndPhoneAndType(
                        serviceProvider.getName(),
                        serviceProvider.getDescription(),
                        serviceProvider.getPhone(),
                        serviceProvider.getType())
                .ifPresent(p -> { throw new ServiceProviderConflictException("Service provider already exists"); });

        Set<Product> products = new HashSet<>();
        for(Product product : serviceProvider.getProducts()) {
            productRepository.findProductByBarcodeAndNameAndDescriptionAndPriceAndType(product.getBarcode(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getType()).ifPresentOrElse(
                    products::add,
                    () -> products.add(productRepository.save(product))
            );
        }

        Location loc = locationRepository.save(serviceProvider.getLocation());

        serviceProvider.setLocation(loc);

        serviceProvider.setProducts(products);
        return serviceProviderRepository.save(serviceProvider);
    }
    public ServiceProvider updateServiceProvider(Long id, ServiceProvider serviceProvider) {
        ServiceProvider existing = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        serviceProviderRepository
                .findServiceProviderByNameAndDescriptionAndPhoneAndType(
                        serviceProvider.getName(),
                        serviceProvider.getDescription(),
                        serviceProvider.getPhone(),
                        serviceProvider.getType())
                .filter(sp -> !sp.getId().equals(id))
                .ifPresent(p -> { throw new ServiceProviderConflictException("Service provider already exists"); });

        existing.setName(serviceProvider.getName());
        existing.setDescription(serviceProvider.getDescription());
        existing.setPhone(serviceProvider.getPhone());
        existing.setType(serviceProvider.getType());
        existing.setImageUrl(serviceProvider.getImageUrl());

        Location loc = locationRepository.save(serviceProvider.getLocation());
        existing.setLocation(loc);

        return serviceProviderRepository.save(existing);
    }

    public ServiceProvider updateServiceProviderProducts(Long id, Set<Product> products) {
        ServiceProvider provider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        Set<Product> result = new HashSet<>();
        for (Product product : products) {
            productRepository.findProductByBarcodeAndNameAndDescriptionAndPriceAndType(
                            product.getBarcode(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getType())
                    .ifPresentOrElse(result::add,
                            () -> result.add(productRepository.save(product)));
        }

        provider.setProducts(result);
        return serviceProviderRepository.save(provider);
    }
}
