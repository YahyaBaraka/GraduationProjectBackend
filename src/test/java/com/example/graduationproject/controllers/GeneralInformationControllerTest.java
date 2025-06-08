package com.example.graduationproject.controllers;

import com.example.graduationproject.services.GeneralInformationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    private GeneralInformationService generalInfoService;

    @Test
    void getInformationHandlesError() throws Exception {
        when(generalInfoService.getAllGeneralInformation()).thenThrow(new RuntimeException("fail"));

        mockMvc.perform(get("/api/general-information"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }
}