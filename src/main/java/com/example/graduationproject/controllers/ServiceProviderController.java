package com.example.graduationproject.controllers;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ServiceProviderService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/service-provider/")
@Log
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("/markets")
    public ResponseEntity<List<ServiceProvider>> getAllMarkets() {
        log.info("Received Request to get service providers with type MARKETS");
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.MARKET));
    }
    @GetMapping("/pharmacies")
    public ResponseEntity<List<ServiceProvider>> getAllPharmacies() {
        log.info("Received Request to get service providers with type PHARMACIES");
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.PHARMACY));
    }
    @GetMapping("/restaurants")
    public ResponseEntity<List<ServiceProvider>> getAllRestaurants() {
        log.info("Received Request to get service providers with type RESTAURANT");
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.RESTAURANT));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        log.info("Received Request to get all service providers");
        return ResponseEntity.ok(serviceProviderService.getAllProviders());
    }
    @PostMapping("/create/provider")
    public ResponseEntity<ServiceProvider> createServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider) {
        log.info("Received Request to create service provider with values:" + serviceProvider);
        return ResponseEntity.ok(serviceProviderService.addServiceProvider(serviceProvider));
    }
    @PostMapping("/create/providers")
    public ResponseEntity<ServiceProvider[]> createServiceProvider(@Valid @RequestBody ServiceProvider[] serviceProviders) {
        log.info("Received Request to create service providers with values: " + Arrays.toString(serviceProviders));
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        for(ServiceProvider serviceProvider : serviceProviders) {
            log.info(serviceProvider.toString());
            ServiceProvider serviceProviderResult = serviceProviderService.addServiceProvider(serviceProvider);
            serviceProviderList.add(serviceProviderResult);
        }
        return ResponseEntity.ok(serviceProviderList.toArray(ServiceProvider[]::new));
    }
    @PostMapping("/{id}")
    public ResponseEntity<ServiceProvider> updateServiceProvider(@PathVariable Long id,
                                                                 @Valid @RequestBody ServiceProvider serviceProvider) {
        log.info("Received Request to update service provider with id: " + id);
        return ResponseEntity.ok(serviceProviderService.updateServiceProvider(id, serviceProvider));
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<ServiceProvider> updateServiceProviderProducts(@PathVariable Long id,
                                                                         @Valid @RequestBody ServiceProvider serviceProvider) {
        log.info("Received Request to update products for service provider " + id);
        return ResponseEntity.ok(serviceProviderService.updateServiceProviderProducts(id, serviceProvider.getProducts()));
    }
}
