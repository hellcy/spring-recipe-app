package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
  Set<UnitOfMeasureCommand> listAllUoms();
}
