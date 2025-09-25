package com.momentum.momentum_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momentum.momentum_back.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
  
  Optional<Pet> findByUserEmail(String email);

}
