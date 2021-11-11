package com.yuancheng.springrecipeapp.models;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
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

}
