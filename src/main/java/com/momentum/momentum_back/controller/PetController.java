package com.momentum.momentum_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentum.momentum_back.dto.PetDTO;
import com.momentum.momentum_back.dto.PointsLedgerDTO;
import com.momentum.momentum_back.service.PetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

  private final PetService petService;

  @PostMapping
  public ResponseEntity<?> createPet(
    @RequestBody @Valid PetDTO petDTO
  ) {

    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO petCreated = petService.createPet(petDTO, userEmail);

    return ResponseEntity.status(HttpStatus.OK)
      .body(petCreated);
  }

  @GetMapping
  public ResponseEntity<?> getPetByUser() {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO pet = petService.readPetByUser(userEmail);

    return ResponseEntity.status(HttpStatus.OK)
      .body(pet);
  }

  @PostMapping("/points")
  public ResponseEntity<?> postPointsByUser(
    @RequestBody @Valid PointsLedgerDTO pointsLedgerDTO
  ) {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    petService.addPointsByUser(pointsLedgerDTO, userEmail);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/experience")
  public ResponseEntity<?> addExperienceToUser(
    @RequestBody Integer experience
  ) {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO updatedPet = petService.addExperienceByUser(experience, userEmail);

    return ResponseEntity.status(HttpStatus.OK).body(updatedPet);
  }

  @PostMapping("/feed")
  public ResponseEntity<?> feedPet() {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO updatedPet = petService.feedPet(userEmail);

    return ResponseEntity.status(HttpStatus.OK).body(updatedPet);
  }

  @PostMapping("/heal")
  public ResponseEntity<?> healPet() {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO updatedPet = petService.healPet(userEmail);

    return ResponseEntity.status(HttpStatus.OK).body(updatedPet);
  }

  @PostMapping("/boost-energy")
  public ResponseEntity<?> boostEnergy() {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    PetDTO updatedPet = petService.boostEnergy(userEmail);

    return ResponseEntity.status(HttpStatus.OK).body(updatedPet);
  }
}
