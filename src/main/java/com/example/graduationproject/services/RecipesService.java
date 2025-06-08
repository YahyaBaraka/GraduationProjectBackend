package com.example.graduationproject.services;

import com.example.graduationproject.model.Recipe;
import com.example.graduationproject.repositrories.RecipesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipesService {
    private final RecipesRepository recipesRepository;

    public RecipesService(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipesRepository.findAll();
    }
    public Recipe saveRecipe(Recipe recipe) {
        return recipesRepository.save(recipe);
    }
}
