package com.yuancheng.springrecipeapp.repositories;

import com.yuancheng.springrecipeapp.models.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
  Optional<UnitOfMeasure> findByDescription(String description);
}
