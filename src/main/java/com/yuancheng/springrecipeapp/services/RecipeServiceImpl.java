package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{
  private final RecipeRepository recipeRepository;

  public RecipeServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  public Set<Recipe> getRecipes() {
    log.debug("I am in the Service");
    Set<Recipe> recipes = new HashSet<>();
    /*
      double colon :: syntax is introduced in Java 8, it is Method Reference
      It is basically a reference to a single method. i.e. it refers to an existing method by name.

      Method references are expressions which have the same treatment as lambda expressions (...),  but instead of providing a method body, they refer an existing method by name.

      The only major criterion to satisfy is: the method you provide should have a similar signature to the method of the functional interface you use as object reference. Below is illegal
      Supplier<Boolean> p = Hey::square; // illegal
     */
    recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
    //recipeRepository.findAll().iterator().forEachRemaining(recipe -> recipes.add(recipe));
    return recipes;
  }

  @Override
  public Recipe findById(Long id) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(id);

    if (!recipeOptional.isPresent()) {
      throw new RuntimeException("Recipe not found!");
    }
    return recipeOptional.get();
  }
}
