package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.services.ImageService;
import com.yuancheng.springrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
  private final RecipeService recipeService;
  private final ImageService imageService;

  public ImageController(RecipeService recipeService, ImageService imageService) {
    this.recipeService = recipeService;
    this.imageService = imageService;
  }

  @GetMapping("/recipe/{recipeId}/image")
  public String showUploadForm(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

    model.addAttribute("recipe", recipeCommand);
    return "/recipe/imageuploadform";
  }

  @PostMapping("/recipe/{recipeId}/image")
  public String handleImagePost(@PathVariable String recipeId,
                                @RequestParam("imagefile")MultipartFile file) {
    imageService.saveImageFile(Long.valueOf(recipeId), file);

    return "redirect:/recipe/" + recipeId + "/show";
  }
}
