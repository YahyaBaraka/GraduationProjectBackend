package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Address;
import com.example.graduationproject.model.Location;
import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.exceptions.ServiceProviderConflictException;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ServiceProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceProviderController.class)
class ServiceProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ServiceProviderService serviceProviderService;

    private ServiceProvider sampleProvider() {
        ServiceProvider sp = new ServiceProvider();
        sp.setId(1L);
        sp.setName("Name");
        sp.setDescription("Desc");
        sp.setPhone("0799999999");
        sp.setType(ProviderType.MARKET);
        sp.setImageUrl("img");
        Address addr = new Address();
        addr.setCity("City");
        addr.setCountry("Country");
        addr.setStreet("Street");

        Location loc = new Location();
        loc.setLatitude(1.0f);
        loc.setLongitude(1.0f);
        loc.setAddress(addr);

        sp.setLocation(loc);
        return sp;
    }

    @Test
    void getAllMarketsHandlesError() throws Exception {
        when(serviceProviderService.getProvidersByType(ProviderType.MARKET))
                .thenThrow(new RuntimeException("fail"));

        mockMvc.perform(get("/api/service-provider/markets"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }

    @Test
    void createServiceProviderHandlesError() throws Exception {
        when(serviceProviderService.addServiceProvider(sampleProvider()))
                .thenThrow(new RuntimeException("fail"));

        mockMvc.perform(post("/api/service-provider/create/provider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void createServiceProviderConflict() throws Exception {
        when(serviceProviderService.addServiceProvider(sampleProvider()))
                .thenThrow(new ServiceProviderConflictException("duplicate"));

        String validJson = mapper.writeValueAsString(sampleProvider());

        mockMvc.perform(post("/api/service-provider/create/provider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("duplicate"));
    }
}
