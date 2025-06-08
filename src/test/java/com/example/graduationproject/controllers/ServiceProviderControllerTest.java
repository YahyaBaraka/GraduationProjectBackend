package com.example.graduationproject.controllers;

import com.example.graduationproject.model.ProviderType;
import com.example.graduationproject.model.ServiceProvider;
import com.example.graduationproject.services.ServiceProviderService;
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

    @MockitoBean
    private ServiceProviderService serviceProviderService;

    private ServiceProvider sampleProvider() {
        ServiceProvider sp = new ServiceProvider();
        sp.setId(1L);
        sp.setName("Name");
        sp.setDescription("Desc");
        sp.setPhone("123");
        sp.setType(ProviderType.MARKET);
        sp.setImageUrl("img");
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
}