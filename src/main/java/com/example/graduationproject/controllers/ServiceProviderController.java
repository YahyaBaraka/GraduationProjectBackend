package com.example.graduationproject.controllers;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/service-provider/")
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        return ResponseEntity.ok(serviceProviderService.getAllProviders());
    }
    @GetMapping("markets")
    public ResponseEntity<List<ServiceProvider>> getAllMarkets() {
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.MARKET));
    }
    @GetMapping("pharmacies")
    public ResponseEntity<List<ServiceProvider>> getAllPharmacies() {
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.PHARMACY));
    }
    @GetMapping("restaurants")
    public ResponseEntity<List<ServiceProvider>> getAllRestaurants() {
        return ResponseEntity.ok(serviceProviderService.getProvidersByType(ProviderType.RESTAURANT));
    }
}
