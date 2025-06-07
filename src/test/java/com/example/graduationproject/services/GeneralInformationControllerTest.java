package com.example.graduationproject.services;

import com.example.graduationproject.controllers.GeneralInformationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneralInformationController.class)
class GeneralInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GeneralInformationService service;

    @Test
    void getInformationHandlesError() throws Exception {
        when(service.getAllGeneralInformation()).thenThrow(new RuntimeException("fail"));

        mockMvc.perform(get("/api/general-information"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }
}