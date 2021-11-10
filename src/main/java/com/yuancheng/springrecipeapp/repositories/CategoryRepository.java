package com.yuancheng.springrecipeapp.repositories;

import com.yuancheng.springrecipeapp.models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
  Optional<Category> findByDescription(String description);
}
