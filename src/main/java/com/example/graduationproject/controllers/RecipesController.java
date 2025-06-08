package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Recipe;
import com.example.graduationproject.services.RecipesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }
    @GetMapping("")
    ResponseEntity<List<Recipe>> getRecipes(){
        return ResponseEntity.ok(recipesService.getAllRecipes());
    }
    @PostMapping("/create")
    ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipes){
        return ResponseEntity.ok(recipesService.saveRecipe(recipes));
    }
    @PostMapping("/create-all")
    ResponseEntity<Recipe[]> createRecipes(@Valid @RequestBody Recipe[] recipes){
        for (Recipe recipe : recipes) {
            recipesService.saveRecipe(recipe);
        }
        return ResponseEntity.ok(recipes);
    }
}
