package com.momentum.momentum_back.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.momentum.momentum_back.dto.PetLevelDTO;
import com.momentum.momentum_back.mapper.PetLevelMapper;
import com.momentum.momentum_back.repository.PetLevelRepository;
import com.momentum.momentum_back.service.PetLevelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetLevelServiceImpl implements PetLevelService {

  private final PetLevelRepository petLevelRepository;
  private final PetLevelMapper petLevelMapper;

  @Override
  public List<PetLevelDTO> getAllLevels() {
    return petLevelRepository.findAll()
      .stream()
      .map(petLevelMapper::toDto)
      .collect(Collectors.toList());
  }
}
