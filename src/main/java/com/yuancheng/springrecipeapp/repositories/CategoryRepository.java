package com.yuancheng.springrecipeapp.repositories;

import com.yuancheng.springrecipeapp.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
