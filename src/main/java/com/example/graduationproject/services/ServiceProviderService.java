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
}
