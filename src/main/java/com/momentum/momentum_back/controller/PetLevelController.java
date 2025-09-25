package com.momentum.momentum_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentum.momentum_back.dto.PetLevelDTO;
import com.momentum.momentum_back.service.PetLevelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet-levels")
@RequiredArgsConstructor
public class PetLevelController {

  private final PetLevelService petLevelService;

  @GetMapping
  public ResponseEntity<List<PetLevelDTO>> getAllLevels() {
    List<PetLevelDTO> levels = petLevelService.getAllLevels();
    return ResponseEntity.status(HttpStatus.OK).body(levels);
  }
}
