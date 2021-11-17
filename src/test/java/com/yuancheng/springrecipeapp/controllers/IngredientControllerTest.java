package com.yuancheng.springrecipeapp.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.services.IngredientService;
import com.yuancheng.springrecipeapp.services.RecipeService;
import com.yuancheng.springrecipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

  @Mock
  RecipeService recipeService;

  @Mock
  IngredientService ingredientService;

  @Mock
  UnitOfMeasureService unitOfMeasureService;

  IngredientController ingredientController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

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

  @Test
  void testShowIngredient() throws Exception{
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();

    // when
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

    // then
    mockMvc.perform(get("/recipe/1/ingredients/2/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/ingredient/show"))
            .andExpect(model().attributeExists("ingredient"));
  }

  @Test
  void testUpdateIngredientForm() throws Exception {
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();

    // when
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
    when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

    // then
    mockMvc.perform(get("/recipe/1/ingredients/2/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/ingredient/ingredientForm"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uomList"));
  }

  @Test
  void testSaveOrUpdate() throws Exception {
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(3L);
    ingredientCommand.setRecipeId(2L);

    // when
    when(ingredientService.saveOrUpdateIngredientCommand(any())).thenReturn(ingredientCommand);

    // then
    mockMvc.perform(post("/recipe/2/ingredient")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2/ingredients/3/show"));
  }

  @Test
  void testNewIngredientForm() throws Exception{
    // given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    // when
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
    when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

    // then
    mockMvc.perform(get("/recipe/1/ingredients/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/ingredient/ingredientForm"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uomList"));

    verify(recipeService, times(1)).findCommandById(anyLong());
  }
}