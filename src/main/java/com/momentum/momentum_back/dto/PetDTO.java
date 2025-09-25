package com.momentum.momentum_back.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

  private Long petId;

  private Long userId;

  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  private Integer level;

  private Integer pointsTotal;

  private Integer experience;

  private String url;
}
