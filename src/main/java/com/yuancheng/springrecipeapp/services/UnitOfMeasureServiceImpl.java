package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.UnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;
  private final UnitOfMeasureToUnitOfMeasureCommand converter;

  public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
    this.unitOfMeasureRepository = unitOfMeasureRepository;
    this.converter = converter;
  }

  @Override
  public Set<UnitOfMeasureCommand> listAllUoms() {
    return StreamSupport.stream(unitOfMeasureRepository.findAll()
            .spliterator(), false)
            .map(unitOfMeasure -> converter.convert(unitOfMeasure))
            .collect(Collectors.toSet());
  }
}
