package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.exceptions.NotFoundException;
import com.yuancheng.springrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {
  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping({"/recipe/{id}/show"})
  public String showById(@PathVariable String id, Model model) {
    model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

    return "recipe/show";
  }

  @GetMapping("recipe/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());

    return "recipe/recipeForm";
  }

  @PostMapping("recipe")
  public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
    RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

    return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
  }

  @GetMapping("/recipe/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

    return "recipe/recipeForm";
  }

  @GetMapping("/recipe/{id}/delete")
  public String deleteRecipe(@PathVariable String id) {
    log.debug("Deleting id: " + id);
    recipeService.deleteById(Long.valueOf(id));

    return "redirect:/";
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(Exception exception) {
    log.error("Handling not found exception");
    log.error(exception.getMessage());

    ModelAndView mav = new ModelAndView();

    mav.setViewName("404error");
    mav.addObject("exception", exception);
    return mav;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NumberFormatException.class)
  public ModelAndView handleNumberFormat(Exception exception) {
    log.error("Handling number format exception.");

    ModelAndView mav = new ModelAndView();
    mav.setViewName("400error");
    mav.addObject("exception", exception);
    return mav;
  }
}
