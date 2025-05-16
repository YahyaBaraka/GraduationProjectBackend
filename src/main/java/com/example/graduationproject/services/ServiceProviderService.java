package com.example.graduationproject.services;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.repositrories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceProviderService {
    @Autowired
    private final ServiceProviderRepository serviceProviderRepository;

    public ServiceProviderService(ServiceProviderRepository serviceProviderRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
    }

    public List<ServiceProvider> getAllProviders() {
        return serviceProviderRepository.findAll();
    }
    public List<ServiceProvider> getProvidersByType(ProviderType providerType) {
        return serviceProviderRepository.getServiceProviderByType(providerType);
    }

    public ServiceProvider addServiceProvider(ServiceProvider serviceProvider) {
        return serviceProviderRepository.save(serviceProvider);
    }
}
