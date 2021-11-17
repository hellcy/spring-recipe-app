package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.converters.IngredientCommandToIngredient;
import com.yuancheng.springrecipeapp.converters.IngredientToIngredientCommand;
import com.yuancheng.springrecipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.yuancheng.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.models.Ingredient;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  UnitOfMeasureRepository unitOfMeasureRepository;

  IngredientService ingredientService;

  public IngredientServiceImplTest() {
    this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
  }

  @Test
  void findByRecipeIdAndIdHappyPath() {
    // given
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(1L);

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(2L);

    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId(3L);

    recipe.addingIngredient(ingredient1);
    recipe.addingIngredient(ingredient2);
    recipe.addingIngredient(ingredient3);

    // when
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    // then
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

    assertEquals(recipe.getId(), ingredientCommand.getRecipeId());
    assertEquals(ingredient3.getId(), ingredientCommand.getId());
    verify(recipeRepository, times(1)).findById(anyLong());
  }

  @Test
  void testSaveRecipeCommand() {
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(3L);
    ingredientCommand.setRecipeId(2L);

    Optional<Recipe> recipeOptional = Optional.of(new Recipe());

    Recipe savedRecipe = new Recipe();
    savedRecipe.addingIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId(3L);

    when(recipeRepository.save(any())).thenReturn(savedRecipe);
    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    // when
    IngredientCommand savedCommand = ingredientService.saveOrUpdateIngredientCommand(ingredientCommand);

    // then
    assertEquals(3L, savedCommand.getId());
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));

  }

  @Test
  void testDeleteById() {
    // given
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Ingredient ingredient = new Ingredient();
    ingredient.setId(2L);
    ingredient.setRecipe(recipe);
    recipe.addingIngredient(ingredient);

    // when
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    ingredientService.deleteById(1L, 2L);

    // then
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
}