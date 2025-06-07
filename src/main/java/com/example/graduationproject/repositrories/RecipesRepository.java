package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipe,Long> {
}
