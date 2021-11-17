package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.commands.UnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.services.IngredientService;
import com.yuancheng.springrecipeapp.services.RecipeService;
import com.yuancheng.springrecipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@Slf4j
@Controller
public class IngredientController {
  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UnitOfMeasureService unitOfMeasureService;

  public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("Getting ingredient list for recipe id: " + recipeId);

    // use command object to avoid lazy load errors in Thymeleaf
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

    return "/recipe/ingredient/list";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredients/{id}/show")
  public String showRecipeIngredient(@PathVariable String recipeId,
                                     @PathVariable String id,
                                     Model model) {
    log.debug("Getting ingredient id: " + id + " for recipe id: " + recipeId);

    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

    return "/recipe/ingredient/show";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredients/{id}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id,
                                       Model model) {
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

    return "/recipe/ingredient/ingredientForm";
  }

  @PostMapping
  @RequestMapping("/recipe/{recipeId}/ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
    IngredientCommand savedCommand = ingredientService.saveOrUpdateIngredientCommand(ingredientCommand);

    log.debug("saved recipe id: " + savedCommand.getRecipeId());
    log.debug("saved ingredient id: " + savedCommand.getId());

    return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
  }

  @GetMapping
  @RequestMapping("recipe/{recipeId}/ingredients/new")
  public String newIngredient(@PathVariable String recipeId, Model model) {

    // make sure we have a good id value
    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
    // todo raise exception if null

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(recipeCommand.getId());
    ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

    model.addAttribute("ingredient", ingredientCommand);

    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

    return "/recipe/ingredient/ingredientForm";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredients/{id}/delete")
  public String deleteIngredient(@PathVariable String recipeId,
                                 @PathVariable String id) {
    ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

    return "redirect:/recipe/" + recipeId + "/ingredients";
  }

}
