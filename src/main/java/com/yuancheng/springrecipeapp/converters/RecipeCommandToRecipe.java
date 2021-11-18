package com.yuancheng.springrecipeapp.converters;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.models.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

  private final IngredientCommandToIngredient ingredientConverter;
  private final CategoryCommandToCategory categoryConverter;
  private final NotesCommandToNotes notesConverter;

  public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientConverter, CategoryCommandToCategory categoryConverter, NotesCommandToNotes notesConverter) {
    this.ingredientConverter = ingredientConverter;
    this.categoryConverter = categoryConverter;
    this.notesConverter = notesConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public Recipe convert(RecipeCommand source) {
    if (source == null) {
      return null;
    }

    final Recipe recipe = new Recipe();
    recipe.setId(source.getId());
    recipe.setCookTime(source.getCookTime());
    recipe.setDescription(source.getDescription());
    recipe.setDifficulty(source.getDifficulty());
    recipe.setPrepTime(source.getPrepTime());
    recipe.setDirections(source.getDirections());
    recipe.setServings(source.getServings());
    recipe.setSource(source.getSource());
    recipe.setUrl(source.getUrl());
    recipe.setNotes(notesConverter.convert(source.getNotes()));
    recipe.setImage(source.getImage());

    if (source.getIngredients() != null && source.getIngredients().size() > 0) {
      source.getIngredients()
              .forEach(ingredientCommand -> recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
    }

    if (source.getCategories() != null && source.getCategories().size() > 0) {
      source.getCategories()
              .forEach(categoryCommand -> recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
    }
    return recipe;
  }
}
