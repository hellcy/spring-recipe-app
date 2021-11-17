package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

  @Mock
  RecipeRepository recipeRepository;

  ImageService imageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    imageService = new ImageServiceImpl(recipeRepository);
  }

  @Test
  void saveImageFile() throws IOException {
    // given
    Long id = 1L;
    MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "sample content".getBytes());

    Recipe recipe = new Recipe();
    recipe.setId(id);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    // when
    when(recipeRepository.findById(id)).thenReturn(recipeOptional);
    imageService.saveImageFile(id, multipartFile);

    // then
    // because save method doesn't return anything, so we need to use ArgumentCaptor to test the saved Recipe
    verify(recipeRepository, times(1)).save(argumentCaptor.capture());
    Recipe savedRecipe = argumentCaptor.getValue();
    assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
  }
}