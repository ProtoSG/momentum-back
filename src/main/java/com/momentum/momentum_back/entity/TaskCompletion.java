package com.momentum.momentum_back.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_completion")
@Check(constraints = "base_points >= 0")
@Check(constraints = "multiplier >= 0")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCompletion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "completion_id")
  private Long completionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "task_id", referencedColumnName = "task_id", nullable = false)
  private Task task;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  private User user;

  @Column(name = "completed_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime completedAt;

  @Column(name = "base_points", nullable = false)
  private Integer basePoints;

  @Column(name = "multiplier", nullable = false)
  private Double multiplier;

  @Column(name = "points_awarded", nullable = false)
  private Integer pointsAwarded;

  @PrePersist
  void applyDefaults() {
    if (multiplier == null) multiplier = 1.00;
  }
}
