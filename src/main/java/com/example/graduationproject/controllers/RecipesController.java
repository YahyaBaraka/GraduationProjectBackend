package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Recipes;
import com.example.graduationproject.services.RecipesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }
    @GetMapping("")
    ResponseEntity<List<Recipes>> getRecipes(){
        return ResponseEntity.ok(recipesService.getAllRecipes());
    }
}
