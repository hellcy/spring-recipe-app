package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.models.Recipe;

import java.util.Set;

public interface RecipeService {
  Set<Recipe> getRecipes();
  Recipe findById(Long id);
  RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
  RecipeCommand findCommandById(Long id);
  void deleteById(Long id);
}
