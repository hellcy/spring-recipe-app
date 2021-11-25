package com.yuancheng.springrecipeapp.bootstrap;

import com.yuancheng.springrecipeapp.models.Category;
import com.yuancheng.springrecipeapp.models.UnitOfMeasure;
import com.yuancheng.springrecipeapp.repositories.CategoryRepository;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class RecipeMySQLDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public RecipeMySQLDataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
    this.categoryRepository = categoryRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (categoryRepository.count() == 0L) {
      log.debug("Loading Categories");
      loadCategories();
    }

    if (unitOfMeasureRepository.count() == 0L) {
      log.debug("Loading UOMs");
      loadUOM();
    }
  }

  private void loadUOM() {
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setDescription("Teaspoon");
    unitOfMeasureRepository.save(uom1);

    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setDescription("Tablespoon");
    unitOfMeasureRepository.save(uom2);

    UnitOfMeasure uom3 = new UnitOfMeasure();
    uom3.setDescription("Cup");
    unitOfMeasureRepository.save(uom3);

    UnitOfMeasure uom4 = new UnitOfMeasure();
    uom4.setDescription("Pinch");
    unitOfMeasureRepository.save(uom4);

    UnitOfMeasure uom6 = new UnitOfMeasure();
    uom6.setDescription("Ounce");
    unitOfMeasureRepository.save(uom6);

    UnitOfMeasure uom7 = new UnitOfMeasure();
    uom7.setDescription("Each");
    unitOfMeasureRepository.save(uom7);

    UnitOfMeasure uom8 = new UnitOfMeasure();
    uom8.setDescription("Dash");
    unitOfMeasureRepository.save(uom8);

    UnitOfMeasure uom9 = new UnitOfMeasure();
    uom9.setDescription("Pint");
    unitOfMeasureRepository.save(uom9);
  }


  private void loadCategories() {
    Category cat1 = new Category();
    cat1.setDescription("American");
    categoryRepository.save(cat1);

    Category cat2 = new Category();
    cat2.setDescription("Italian");
    categoryRepository.save(cat2);

    Category cat3 = new Category();
    cat3.setDescription("Mexican");
    categoryRepository.save(cat3);

    Category cat4 = new Category();
    cat4.setDescription("Fast food");
    categoryRepository.save(cat4);
  }


}
