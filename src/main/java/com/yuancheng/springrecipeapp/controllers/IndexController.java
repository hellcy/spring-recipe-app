package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.models.Category;
import com.yuancheng.springrecipeapp.models.UnitOfMeasure;
import com.yuancheng.springrecipeapp.repositories.CategoryRepository;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import com.yuancheng.springrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {
  private final RecipeService recipeService;

  public IndexController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping({"", "/", "/index"})
  public String getIndexPage(Model model) {
    log.debug("Getting index page.");
    model.addAttribute("recipes", recipeService.getRecipes());
    return "index";
  }
}
