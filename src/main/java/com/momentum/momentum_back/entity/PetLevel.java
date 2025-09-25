package com.momentum.momentum_back.entity;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_level")
@Check(constraints = "experience_required >= 0")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetLevel {

  @Id
  @Column(name = "level")
  private Integer level;

  @Column(name = "experience_required", nullable = false)
  private Integer experienceRequired;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

}
