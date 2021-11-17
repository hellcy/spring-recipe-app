package com.yuancheng.springrecipeapp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;

  @Lob
  private String directions;

  /*
    EnumType.STRING: value in DB will be saved as String
    EnumType.ORDINAL: value in DB will be saved as Number
   */
  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;

  @Lob
  private Byte[] image;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();

  /*
    Recipe contains Notes, if we delete the Recipe, we want to delete the Notes as well
   */
  @OneToOne(cascade = CascadeType.ALL)
  private Notes notes;

  @ManyToMany
  @JoinTable(name = "recipe_category",
          joinColumns = @JoinColumn(name = "recipe_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public void setNotes(Notes notes) {
    if (notes != null) {
      this.notes = notes;
      notes.setRecipe(this);
    }
  }

  public Recipe addingIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
    ingredient.setRecipe(this);
    return this;
  }

  public Recipe removingIngredient(Ingredient ingredient) {
    this.ingredients.remove(ingredient);
    return this;
  }
}
