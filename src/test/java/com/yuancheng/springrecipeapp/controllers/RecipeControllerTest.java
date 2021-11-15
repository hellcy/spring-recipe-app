package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

  @Mock
  RecipeService recipeService;

  @InjectMocks
  RecipeController recipeController;

  @BeforeEach
  void setUp() {

  }

  @Test
  void getRecipeTest() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/show/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"));
  }
}