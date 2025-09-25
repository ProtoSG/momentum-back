package com.momentum.momentum_back.mapper;

import org.springframework.stereotype.Component;

import com.momentum.momentum_back.dto.PointsLedgerDTO;
import com.momentum.momentum_back.entity.PointsLedger;

@Component
public class PointsLedgerMapper {

  public PointsLedgerDTO toDto(PointsLedger pointsLedger) {
    if (pointsLedger == null) {
      return null;
    }

    return PointsLedgerDTO.builder()
      .ledgerId(pointsLedger.getLedgerId())
      .userId(pointsLedger.getUser().getUserId())
      .petId(pointsLedger.getPet().getPetId())
      .amount(pointsLedger.getAmount())
      .reason(pointsLedger.getReason())
      .refType(pointsLedger.getRefType())
      .refId(pointsLedger.getRef_id())
      .build();
  }

  public PointsLedger toEntity(PointsLedgerDTO pointsLedgerDTO) {
    if (pointsLedgerDTO == null) return null;

    return PointsLedger.builder()
      .ledgerId(pointsLedgerDTO.getLedgerId())
      .amount(pointsLedgerDTO.getAmount())
      .reason(pointsLedgerDTO.getReason())
      .refType(pointsLedgerDTO.getRefType())
      .ref_id(pointsLedgerDTO.getRefId())
      .build();
  }
}
