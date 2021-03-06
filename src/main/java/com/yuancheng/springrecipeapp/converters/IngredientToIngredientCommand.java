package com.yuancheng.springrecipeapp.converters;

import com.yuancheng.springrecipeapp.commands.IngredientCommand;
import com.yuancheng.springrecipeapp.models.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

  private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

  public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
    this.uomConverter = uomConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public IngredientCommand convert(Ingredient source) {
    if (source == null) {
      return null;
    }
    final IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(source.getId());
    ingredientCommand.setAmount(source.getAmount());
    ingredientCommand.setDescription(source.getDescription());
    ingredientCommand.setUom(uomConverter.convert(source.getUom()));
    if (source.getRecipe() != null) {
      ingredientCommand.setRecipeId(source.getRecipe().getId());
    }
    return ingredientCommand;
  }
}
