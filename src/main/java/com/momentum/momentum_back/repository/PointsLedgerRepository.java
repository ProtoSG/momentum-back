package com.momentum.momentum_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.momentum.momentum_back.entity.PointsLedger;

public interface PointsLedgerRepository extends JpaRepository<PointsLedger, Long> {

  @Query("SELECT COALESCE(SUM(pl.amount), 0) FROM PointsLedger pl WHERE pl.pet.id = :petId")
  Integer sumByPetId(Long petId);

}
