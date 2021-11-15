package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.converters.IngredientToIngredientCommand;
import com.yuancheng.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.models.Ingredient;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

  private final IngredientToIngredientCommand ingredientToIngredientCommand;

  @Mock
  RecipeRepository recipeRepository;

  IngredientService ingredientService;

  public IngredientServiceImplTest() {
    this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
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
}