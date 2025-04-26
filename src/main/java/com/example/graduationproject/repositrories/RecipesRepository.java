package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipes,Long> {
}
