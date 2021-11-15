package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.converters.RecipeCommandToRecipe;
import com.yuancheng.springrecipeapp.converters.RecipeToRecipeCommand;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RecipeServiceIT {
  public static final String NEW_DESCRIPTION = "New Description";

  @Autowired
  RecipeService recipeService;

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Transactional
  @Test
  void testSaveOfDescription() {
    // given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    // when
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

    // then
    assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
  }
}