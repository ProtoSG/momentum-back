package com.momentum.momentum_back.mapper;

import org.springframework.stereotype.Component;

import com.momentum.momentum_back.dto.PetLevelDTO;
import com.momentum.momentum_back.entity.PetLevel;

@Component
public class PetLevelMapper {

  public PetLevelDTO toDto(PetLevel petLevel) {
    if (petLevel == null) {
      return null;
    }

    PetLevelDTO dto = new PetLevelDTO();
    dto.setLevel(petLevel.getLevel());
    dto.setExperienceRequired(petLevel.getExperienceRequired());
    dto.setName(petLevel.getName());
    dto.setDescription(petLevel.getDescription());
    
    return dto;
  }

  public PetLevel toEntity(PetLevelDTO dto) {
    if (dto == null) {
      return null;
    }

    PetLevel entity = new PetLevel();
    entity.setLevel(dto.getLevel());
    entity.setExperienceRequired(dto.getExperienceRequired());
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    
    return entity;
  }
}
