package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import com.yuancheng.springrecipeapp.services.RecipeService;
import com.yuancheng.springrecipeapp.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndexControllerTest {

  @Mock
  RecipeService recipeService;

  @Mock
  Model model;

  IndexController indexController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  void getIndexPage() {
    //given
    Set<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());
    Recipe secondRecipe = new Recipe();
    secondRecipe.setId(2L);
    recipes.add(secondRecipe);

    when(recipeService.getRecipes()).thenReturn(recipes);

    /*
      ArgumentCaptor allows us to capture an argument passed to a method in order to inspect it.
      This is especially useful when we can't access the argument
      outside the method we'd like to test.
     */
    ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

    // when
    String viewName = indexController.getIndexPage(model);

    // then
    assertEquals("index", viewName);
    verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    verify(recipeService, times(1)).getRecipes();

    Set<Recipe> setInController = argumentCaptor.getValue();
    assertEquals(2, setInController.size());
  }
}