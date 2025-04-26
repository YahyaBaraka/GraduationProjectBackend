package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> getServiceProviderByType(ProviderType providerType);
}
