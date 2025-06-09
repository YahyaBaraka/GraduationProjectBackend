package com.example.graduationproject.services;

import com.example.graduationproject.exceptions.ServiceProviderConflictException;
import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.repositrories.LocationRepository;
import com.example.graduationproject.repositrories.ProductRepository;
import com.example.graduationproject.repositrories.ServiceProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceProviderServiceTest {

    @Mock
    private ServiceProviderRepository serviceProviderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private ServiceProviderService service;

    private ServiceProvider sampleProvider;

    @BeforeEach
    void setUp() {
        sampleProvider = new ServiceProvider();
        sampleProvider.setId(1L);
        sampleProvider.setName("Name");
        sampleProvider.setDescription("Desc");
        sampleProvider.setPhone("123");
        sampleProvider.setType(ProviderType.MARKET);
        sampleProvider.setImageUrl("img");
    }

    @Test
    void addServiceProviderThrowsWhenDuplicate() {
        when(serviceProviderRepository.findServiceProviderByNameAndDescriptionAndPhoneAndType(
                sampleProvider.getName(),
                sampleProvider.getDescription(),
                sampleProvider.getPhone(),
                sampleProvider.getType()))
                .thenReturn(Optional.of(sampleProvider));

        assertThrows(ServiceProviderConflictException.class, () -> service.addServiceProvider(sampleProvider));

        verify(serviceProviderRepository).findServiceProviderByNameAndDescriptionAndPhoneAndType(
                sampleProvider.getName(),
                sampleProvider.getDescription(),
                sampleProvider.getPhone(),
                sampleProvider.getType());
        verifyNoMoreInteractions(productRepository, locationRepository);
    }
}