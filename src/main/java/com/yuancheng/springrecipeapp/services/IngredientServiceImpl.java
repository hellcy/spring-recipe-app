package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.converters.IngredientCommandToIngredient;
import com.yuancheng.springrecipeapp.converters.IngredientToIngredientCommand;
import com.yuancheng.springrecipeapp.models.Ingredient;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final RecipeRepository recipeRepository;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
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
            .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

    if (!ingredientCommandOptional.isPresent()) {
      throw new RuntimeException("Ingredient not found.");
    }

    return ingredientCommandOptional.get();
  }

  @Override
  @Transactional
  public IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

    if (!recipeOptional.isPresent()) {
      log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
      return new IngredientCommand();
    } else {
      Recipe recipe = recipeOptional.get();

      Optional<Ingredient> ingredientOptional = recipe
              .getIngredients()
              .stream()
              .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
              .findFirst();

      if (!ingredientOptional.isPresent()) {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        ingredient.setRecipe(recipe);
        recipe.addingIngredient(ingredient);

      } else {
        Ingredient ingredientFound = ingredientOptional.get();
        ingredientFound.setDescription(ingredientCommand.getDescription());
        ingredientFound.setAmount(ingredientCommand.getAmount());
        ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure()
                .getId())
                .orElseThrow(() -> new RuntimeException("UOM not found.")));
      }

      Recipe savedRecipe = recipeRepository.save(recipe);

      Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
              .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
              .findFirst();

      // new ingredient doesn't have id
      // savedIngredientOptional will be null if it is new ingredient
      if (!savedIngredientOptional.isPresent()) {
        // check by every property
        // not totally save... but best guess
        savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                .findFirst();
      }

      return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }
  }


}
