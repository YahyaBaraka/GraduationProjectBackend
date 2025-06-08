package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Recipe;
import com.example.graduationproject.services.RecipesService;
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
    ResponseEntity<Recipe> createRecipes(Recipe recipes){
        return ResponseEntity.ok(recipesService.saveRecipes(recipes));
    }
    @PostMapping("/create-all")
    ResponseEntity<Recipe[]> createRecipes(@RequestBody Recipe[] recipes){
        for (Recipe recipe : recipes) {
            recipesService.saveRecipes(recipe);
        }
        return ResponseEntity.ok(recipes);
    }
}
