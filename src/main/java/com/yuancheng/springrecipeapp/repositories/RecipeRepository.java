package com.yuancheng.springrecipeapp.repositories;

import com.yuancheng.springrecipeapp.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
