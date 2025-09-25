package com.momentum.momentum_back.service;

import com.momentum.momentum_back.dto.PetDTO;
import com.momentum.momentum_back.dto.PointsLedgerDTO;

public interface PetService {

  PetDTO createPet(PetDTO petDTO, String email);

  PetDTO readPetByIdAndUser(Long petId, String email);

  PetDTO readPetByUser(String email);

  void addPointsByUser(PointsLedgerDTO pointsLedgerDTO, String email);

  PetDTO addExperienceByUser(Integer experience, String email);

}
