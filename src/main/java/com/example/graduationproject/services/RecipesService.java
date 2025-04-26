package com.example.graduationproject.services;

import com.example.graduationproject.model.Recipes;
import com.example.graduationproject.repositrories.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipesService {
    private final RecipesRepository recipesRepository;

    public RecipesService(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    public List<Recipes> getAllRecipes() {
        return recipesRepository.findAll();
    }
}
