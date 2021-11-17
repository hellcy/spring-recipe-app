package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.UnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.yuancheng.springrecipeapp.models.UnitOfMeasure;
import com.yuancheng.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {
  @Mock
  UnitOfMeasureRepository unitOfMeasureRepository;

  UnitOfMeasureService unitOfMeasureService;

  UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, converter);
  }

  @Test
  void testListAllUoms() {
    // given
    Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
    UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
    unitOfMeasure1.setId(1L);
    unitOfMeasures.add(unitOfMeasure1);

    UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
    unitOfMeasure2.setId(2L);
    unitOfMeasures.add(unitOfMeasure2);

    when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

    // when
    Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();

    // then
    assertEquals(2, commands.size());
    verify(unitOfMeasureRepository, times(1)).findAll();
  }
}