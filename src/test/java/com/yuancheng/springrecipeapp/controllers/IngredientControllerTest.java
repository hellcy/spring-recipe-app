package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

  @Mock
  RecipeService recipeService;

  IngredientController ingredientController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ingredientController = new IngredientController(recipeService);

    mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
  }

  @Test
  void listIngredients() throws Exception{
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(2L);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
    mockMvc.perform(get("/recipe/2/ingredients"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/ingredient/list"))
            .andExpect(model().attributeExists("recipe"));
  }
}