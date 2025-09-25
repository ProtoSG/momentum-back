package com.momentum.momentum_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class PetLevelDTO {

  private Integer level;

  private Integer experienceRequired;

  private String name;

  private String description;

}
