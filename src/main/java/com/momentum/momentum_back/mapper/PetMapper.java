package com.momentum.momentum_back.mapper;

import org.springframework.stereotype.Component;

import com.momentum.momentum_back.dto.PetDTO;
import com.momentum.momentum_back.entity.Pet;

@Component
public class PetMapper {

  public PetDTO toDto(Pet pet) {
    if (pet == null) {
      return null;
    }

    return PetDTO.builder()
      .petId(pet.getPetId())
      .userId(pet.getUser().getUserId())
      .name(pet.getName())
      .level(pet.getLevel().getLevel())
      .pointsTotal(pet.getPointsTotal())
      .experience(pet.getExperience())
      .url(pet.getUrl())
      .build();
  }

  public Pet toEntity(PetDTO petDTO) {
    if (petDTO == null) return null;

    return Pet.builder()
      .petId(petDTO.getPetId())
      .name(petDTO.getName())
      .pointsTotal(petDTO.getPointsTotal())
      .experience(petDTO.getExperience())
      .url(petDTO.getUrl())
      .build();
  }
}
