package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Recipe;
import com.example.graduationproject.services.RecipesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipesController.class)
class RecipesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipesService recipesService;

    private Recipe sampleRecipe() {
        Recipe r = new Recipe();
        r.setId(1L);
        r.setTitle("title");
        r.setContent("content");
        r.setImageUrl("img");
        return r;
    }

    @Test
    void getRecipesHandlesError() throws Exception {
        when(recipesService.getAllRecipes()).thenThrow(new RuntimeException("fail"));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("fail"));
    }

    @Test
    void createRecipesHandlesError() throws Exception {
        when(recipesService.saveRecipe(sampleRecipe())).thenThrow(new RuntimeException("fail"));

        mockMvc.perform(post("/api/recipes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }
}