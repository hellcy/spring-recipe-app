package com.yuancheng.springrecipeapp.models;

import javax.persistence.*;

@Entity
public class Notes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*
    we will not have Cascade here because Notes is part of a Recipe and if we delete the Notes,
    we don't want to delete the Recipe that was owning it
   */
  @OneToOne
  private Recipe recipe;

  @Lob
  private String recipeNotes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public String getRecipeNotes() {
    return recipeNotes;
  }

  public void setRecipeNotes(String recipeNotes) {
    this.recipeNotes = recipeNotes;
  }
}
