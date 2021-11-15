package com.yuancheng.springrecipeapp.converters;

import com.yuancheng.springrecipeapp.commands.CategoryCommand;
import com.yuancheng.springrecipeapp.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

  public static final Long ID_VALUE = new Long(1L);
  public static final String DESCRIPTION = "description";

  CategoryCommandToCategory converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryCommandToCategory();
  }

  @Test
  void convert() {
    // given
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID_VALUE);
    categoryCommand.setDescription(DESCRIPTION);

    Category category = converter.convert(categoryCommand);

    assertEquals(ID_VALUE, category.getId());
    assertEquals(DESCRIPTION, category.getDescription());
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new CategoryCommand()));
  }
}