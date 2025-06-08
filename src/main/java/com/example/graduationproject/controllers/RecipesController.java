package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Recipe;
import com.example.graduationproject.services.RecipesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
@Slf4j
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
    ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe){
        return ResponseEntity.ok(recipesService.saveRecipe(recipe));
    }
    @PostMapping("/create-all")
    ResponseEntity<Recipe[]> createRecipes(@Valid @RequestBody Recipe[] recipes){
        log.info("Received Request to create recipes with values: " + Arrays.toString(recipes));
        for (Recipe recipe : recipes) {
            recipesService.saveRecipe(recipe);
        }
        return ResponseEntity.ok(recipes);
    }
}
