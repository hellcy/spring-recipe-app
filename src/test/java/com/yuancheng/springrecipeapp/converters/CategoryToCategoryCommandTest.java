package com.yuancheng.springrecipeapp.converters;

import com.yuancheng.springrecipeapp.commands.CategoryCommand;
import com.yuancheng.springrecipeapp.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

  public static final Long ID_VALUE = 1L;
  public static final String DESCRIPTION = "description";
  CategoryToCategoryCommand converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryToCategoryCommand();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Category()));
  }

  @Test
  void convert() {
    // given
    Category category = new Category();
    category.setId(ID_VALUE);
    category.setDescription(DESCRIPTION);

    // when
    CategoryCommand categoryCommand = converter.convert(category);

    // then
    assertEquals(ID_VALUE, categoryCommand.getId());
    assertEquals(DESCRIPTION, categoryCommand.getDescription());
  }
}