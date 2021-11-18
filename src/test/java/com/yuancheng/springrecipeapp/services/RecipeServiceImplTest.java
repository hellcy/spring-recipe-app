package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.converters.RecipeCommandToRecipe;
import com.yuancheng.springrecipeapp.converters.RecipeToRecipeCommand;
import com.yuancheng.springrecipeapp.exceptions.NotFoundException;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Mock
  RecipeCommandToRecipe recipeCommandToRecipe;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  void getRecipeByIdTest() {
    Recipe expectedRecipe = new Recipe();
    expectedRecipe.setId(1L);
    Optional<Recipe> recipeOptional = Optional.of(expectedRecipe);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    Recipe returnedRecipe = recipeService.findById(1L);

    assertNotNull(returnedRecipe, "Null recipe returned.");
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void getRecipes() throws Exception {
    Recipe recipe = new Recipe();
    HashSet recipeData = new HashSet();
    recipeData.add(recipe);

    /*
      when findAll method in recipeRepository is being called, return recipeData
     */
    when(recipeRepository.findAll()).thenReturn(recipeData);

    // here we are calling getRecipe, if RecipeRepository is being injected properly
    // it should call findAll method and get recipeData back
    Set<Recipe> recipes = recipeService.getRecipes();
    assertEquals(1, recipes.size());

    // verify if the return data recipes has size 1
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  void deleteById() {

    // given
    Long idToDelete = Long.valueOf(2L);

    // when
    recipeService.deleteById(idToDelete);

    // then
    verify(recipeRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void getRecipeByIdNotFound() throws Exception{
    Optional<Recipe> recipeOptional = Optional.empty();
    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    // should throw exception
    assertThrows(NotFoundException.class, () -> {
      Recipe recipe = recipeService.findById(1L);
    });
  }
}