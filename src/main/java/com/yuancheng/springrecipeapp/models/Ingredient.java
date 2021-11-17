package com.yuancheng.springrecipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal amount;

  @ManyToOne()
  private Recipe recipe;

  /*
    fetch UnitOfMeasure up front when Ingredient is created every time.
   */
  @OneToOne(fetch = FetchType.EAGER)
  private UnitOfMeasure uom;

  public Ingredient() {
  }

  public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
    this.description = description;
    this.amount = amount;
    this.uom = uom;
  }

  public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
    this.description = description;
    this.amount = amount;
    this.recipe = recipe;
    this.uom = uom;
  }
}
