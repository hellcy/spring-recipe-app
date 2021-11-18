package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.services.ImageService;
import com.yuancheng.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

  @Mock
  ImageService imageService;

  @Mock
  RecipeService recipeService;

  ImageController imageController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    imageController = new ImageController(recipeService, imageService);
    mockMvc = MockMvcBuilders
            .standaloneSetup(imageController)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
  }

  @Test
  void showUploadForm() throws Exception {
    // given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    // when
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    // then
    mockMvc.perform(get("/recipe/1/image"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/imageuploadform"))
            .andExpect(model().attributeExists("recipe"));

    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  void handleImagePost() throws Exception{
    MockMultipartFile multipartFile =
            new MockMultipartFile("imagefile", "testing.txt", "text/plain", "sample content".getBytes());

    mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/recipe/1/show"));

    verify(imageService, times(1)).saveImageFile(anyLong(), any());
  }

  @Test
  void renderImageFromDB() throws Exception{
    // given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    String s = "fake image content";
    Byte[] byteObjects = new Byte[s.getBytes().length];
    int i = 0;
    for (byte b : s.getBytes()) {
      byteObjects[i++] = b;
    }
    recipeCommand.setImage(byteObjects);

    // when
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    // then
    MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
            .andExpect(status().isOk())
            .andReturn().getResponse();

    byte[] byteArray = response.getContentAsByteArray();
    assertEquals(s.getBytes().length, byteArray.length);
  }

  @Test
  void testGetImageNumberFormatException() throws Exception{
    mockMvc.perform(get("/recipe/bad/image"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400error"));

    mockMvc.perform(get("/recipe/bad/recipeimage"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400error"));
  }
}