package com.momentum.momentum_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.momentum.momentum_back.entity.PetLevel;

public interface PetLevelRepository extends JpaRepository<PetLevel, Integer> {

  @Query("select max(pl.level) from PetLevel pl where pl.experienceRequired <= :experience") 
  Integer findMaxLevelByExperience(Integer experience);

}
