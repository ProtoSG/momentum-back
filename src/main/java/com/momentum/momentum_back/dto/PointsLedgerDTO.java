package com.momentum.momentum_back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class PointsLedgerDTO {

  private Long ledgerId;

  private Long userId;

  private Long petId;

  @NotNull(message = "El amount es obligatorio")
  @Positive(message = "El amount debe ser mayor que cero")
  private Integer amount;

  @NotBlank(message = "El reason no puede estar vacío")
  private String reason;

  private String refType;

  private Long refId;

}
