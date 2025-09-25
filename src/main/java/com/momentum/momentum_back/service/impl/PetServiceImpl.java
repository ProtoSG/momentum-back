package com.momentum.momentum_back.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.momentum_back.dto.PetDTO;
import com.momentum.momentum_back.dto.PointsLedgerDTO;
import com.momentum.momentum_back.entity.Pet;
import com.momentum.momentum_back.entity.PetLevel;
import com.momentum.momentum_back.entity.PointsLedger;
import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.exceptions.ResourceNotFoundException;
import com.momentum.momentum_back.exceptions.UnauthorizedException;
import com.momentum.momentum_back.mapper.PetMapper;
import com.momentum.momentum_back.mapper.PointsLedgerMapper;
import com.momentum.momentum_back.repository.PetLevelRepository;
import com.momentum.momentum_back.repository.PetRepository;
import com.momentum.momentum_back.repository.PointsLedgerRepository;
import com.momentum.momentum_back.repository.UserRepository;
import com.momentum.momentum_back.service.PetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

  private final PetRepository petRepository;
  private final UserRepository userRepository;
  private final PetLevelRepository petLevelRepository;
  private final PointsLedgerRepository pointsLedgerRepository;
  private final PetMapper petMapper;
  private final PointsLedgerMapper pointsLedgerMapper;
  
  @Transactional
  public PetDTO readPetByIdAndUser(Long id, String email) {
  
    Pet pet = petRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    if(!pet.getUser().getEmail().equals(email)) {
      throw new UnauthorizedException("No autorizado");
    }

    return petMapper.toDto(pet);
  }

  @Transactional
  public PetDTO createPet(PetDTO petDTO, String email) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Integer levelId = petDTO.getLevel() != null ? petDTO.getLevel() : 1;
    PetLevel petLevel =  petLevelRepository.findById(levelId)
      .orElseThrow(() -> new ResourceNotFoundException("Level not found"));

    Pet pet = petMapper.toEntity(petDTO);
    pet.setUser(user);
    pet.setLevel(petLevel);
    
    // Ensure proper initialization
    if (pet.getHealth() == null) pet.setHealth(100);
    if (pet.getEnergy() == null) pet.setEnergy(100);
    if (pet.getHunger() == null) pet.setHunger(100);

    Pet petSaved = petRepository.save(pet);

    return petMapper.toDto(petSaved);
  }

  @Transactional
  public PetDTO readPetByUser(String email) {
    userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    // Fix null values for existing pets
    boolean needsUpdate = false;
    if (pet.getHealth() == null) {
      pet.setHealth(100);
      needsUpdate = true;
    }
    if (pet.getEnergy() == null) {
      pet.setEnergy(100);
      needsUpdate = true;
    }
    if (pet.getHunger() == null) {
      pet.setHunger(100);
      needsUpdate = true;
    }

    if (needsUpdate) {
      pet = petRepository.save(pet);
    }

    return petMapper.toDto(pet);
  }

  @Transactional
  public void addPointsByUser(PointsLedgerDTO pointsLedgerDTO, String email) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    PointsLedger pointsLedger = pointsLedgerMapper.toEntity(pointsLedgerDTO);
    pointsLedger.setUser(user);
    pointsLedger.setPet(pet);

    pointsLedgerRepository.save(pointsLedger);

    Integer total = pointsLedgerRepository.sumByPetId(pet.getPetId());

    pet.setPointsTotal(total);

    petRepository.save(pet);
  }

  @Transactional
  public PetDTO addExperienceByUser(Integer experience, String email) {
    userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    Integer currentExp = pet.getExperience() != null ? pet.getExperience() : 0;
    pet.setExperience(currentExp + experience);

    checkAndUpdateLevel(pet);

    Pet savedPet = petRepository.save(pet);
    return petMapper.toDto(savedPet);
  }

  private void checkAndUpdateLevel(Pet pet) {
    Integer currentExp = pet.getExperience();
    Integer currentLevel = pet.getLevel().getLevel();
    
    Integer newLevel = petLevelRepository.findMaxLevelByExperience(currentExp);
    if (newLevel == null) {
      newLevel = 1;
    }

    if (newLevel > currentLevel) {
      final Integer finalNewLevel = newLevel;
      PetLevel petLevel = petLevelRepository.findById(finalNewLevel)
        .orElseThrow(() -> new ResourceNotFoundException("Level " + finalNewLevel + " not found"));

      pet.setLevel(petLevel);
    }
  }

  @Transactional
  public PetDTO feedPet(String email) {
    userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    if (pet.getPointsTotal() < 10) {
      throw new IllegalStateException("No tienes suficientes puntos para alimentar la mascota");
    }

    if (pet.getHunger() <= 0) {
      throw new IllegalStateException("La mascota ya está muy hambrienta");
    }

    // Deducir puntos
    PointsLedger pointsEntry = PointsLedger.builder()
      .user(pet.getUser())
      .pet(pet)
      .amount(-10)
      .reason("Alimentar mascota")
      .build();
    pointsLedgerRepository.save(pointsEntry);

    // Actualizar stats (aumentar hambre +10, reducir salud -5)
    pet.setHunger(Math.min(100, pet.getHunger() + 10));
    pet.setHealth(Math.max(0, pet.getHealth() - 5));
    pet.setPointsTotal(pet.getPointsTotal() - 10);

    Pet savedPet = petRepository.save(pet);
    return petMapper.toDto(savedPet);
  }

  @Transactional
  public PetDTO healPet(String email) {
    userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    if (pet.getPointsTotal() < 20) {
      throw new IllegalStateException("No tienes suficientes puntos para curar la mascota");
    }

    if (pet.getHealth() >= 100) {
      throw new IllegalStateException("La mascota ya tiene la salud completa");
    }

    // Deducir puntos
    PointsLedger pointsEntry = PointsLedger.builder()
      .user(pet.getUser())
      .pet(pet)
      .amount(-20)
      .reason("Curar mascota")
      .build();
    pointsLedgerRepository.save(pointsEntry);

    // Actualizar stats
    pet.setHealth(Math.min(100, pet.getHealth() + 30));
    pet.setPointsTotal(pet.getPointsTotal() - 20);

    Pet savedPet = petRepository.save(pet);
    return petMapper.toDto(savedPet);
  }

  @Transactional
  public PetDTO boostEnergy(String email) {
    userRepository.findByEmail(email)
      .orElseThrow(() -> new UnauthorizedException("User not authorized"));

    Pet pet = petRepository.findByUserEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

    if (pet.getPointsTotal() < 15) {
      throw new IllegalStateException("No tienes suficientes puntos para aumentar la energía");
    }

    if (pet.getEnergy() >= 100) {
      throw new IllegalStateException("La mascota ya tiene la energía completa");
    }

    // Deducir puntos
    PointsLedger pointsEntry = PointsLedger.builder()
      .user(pet.getUser())
      .pet(pet)
      .amount(-15)
      .reason("Aumentar energía")
      .build();
    pointsLedgerRepository.save(pointsEntry);

    // Actualizar stats
    pet.setEnergy(Math.min(100, pet.getEnergy() + 25));
    pet.setPointsTotal(pet.getPointsTotal() - 15);

    Pet savedPet = petRepository.save(pet);
    return petMapper.toDto(savedPet);
  }
}
