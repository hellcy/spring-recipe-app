package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;

public interface IngredientService {
  IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
  IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);
}
