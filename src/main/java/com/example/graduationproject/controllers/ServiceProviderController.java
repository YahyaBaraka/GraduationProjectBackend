package com.example.graduationproject.controllers;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ServiceProviderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.MARKET));
    }
    @GetMapping("/pharmacies")
    public ResponseEntity<List<ServiceProvider>> getAllPharmacies() {
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.PHARMACY));
    }
    @GetMapping("/restaurants")
    public ResponseEntity<List<ServiceProvider>> getAllRestaurants() {
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.RESTAURANT));
    }
    @PostMapping("/create/provider")
    public ResponseEntity<ServiceProvider> createServiceProvider(@RequestBody ServiceProvider serviceProvider) {
        log.info(serviceProvider.toString());
        return ResponseEntity.ok(serviceProviderService.addServiceProvider(serviceProvider));
    }
    @PostMapping("/create/providers")
    public ResponseEntity<ServiceProvider[]> createServiceProvider(@RequestBody ServiceProvider[] serviceProviders) {
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        ServiceProvider[] serviceProviderArray = serviceProviders;
        for(ServiceProvider serviceProvider : serviceProviders) {
            log.info(serviceProvider.toString());
            serviceProviderService.addServiceProvider(serviceProvider);
        }
        return ResponseEntity.ok(serviceProviderList.toArray(ServiceProvider[]::new));
    }
}
