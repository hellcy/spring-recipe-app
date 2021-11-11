package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository);
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
}