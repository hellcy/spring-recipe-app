package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.converters.IngredientToIngredientCommand;
import com.yuancheng.springrecipeapp.converters.RecipeToRecipeCommand;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

  private final RecipeRepository recipeRepository;
  private final IngredientToIngredientCommand converter;

  public IngredientServiceImpl(RecipeRepository recipeRepository , IngredientToIngredientCommand converter) {
    this.recipeRepository = recipeRepository;
    this.converter = converter;
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (!recipeOptional.isPresent()) {
      throw new RuntimeException("Recipe not found.");
    }

    Recipe recipe = recipeOptional.get();

    Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .map(ingredient -> converter.convert(ingredient)).findFirst();

    if (!ingredientCommandOptional.isPresent()) {
      throw new RuntimeException("Ingredient not found.");
    }

    return ingredientCommandOptional.get();
  }
}
