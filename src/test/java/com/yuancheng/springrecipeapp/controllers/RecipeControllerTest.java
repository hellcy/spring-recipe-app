package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.exceptions.NotFoundException;
import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

  @Mock
  RecipeService recipeService;

  @InjectMocks
  RecipeController recipeController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  @Test
  void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void testGetRecipeNotFound() throws Exception{
    when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

    mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("404error"));
  }

  @Test
  void testGetRecipeNumberFormatException() throws Exception{
    mockMvc.perform(get("/recipe/bad/show"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400error"));
  }

  @Test
  void testGetNewRecipeForm() throws Exception {
    RecipeCommand recipeCommand;

    mockMvc.perform(get("/recipe/new"))
            .andExpect(view().name("recipe/recipeForm"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void testPostNewRecipeForm() throws Exception{
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(2L);

    when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

    mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2/show"));
  }

  @Test
  void testGetUpdateView() throws Exception{
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(2L);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    mockMvc.perform(get("/recipe/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeForm"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void testDeletionAction() throws Exception{
    mockMvc.perform(get("/recipe/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));

    verify(recipeService, times(1)).deleteById(anyLong());
  }
}