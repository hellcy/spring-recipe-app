package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.models.Recipe;

import java.util.Set;

public interface RecipeService {
  Set<Recipe> getRecipes();
  Recipe findById(Long id);
}
