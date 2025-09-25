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
@Table(name = "streak_rule")
@Check(constraints = "multiplier >= 1.00")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreakRule {

  @Id
  @Column(name = "min_days")
  private Long minDays;

  @Column(name = "multiplier", nullable = false)
  private Double multiplier;

}
