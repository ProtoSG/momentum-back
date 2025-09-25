package com.momentum.momentum_back.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "streak")
@Check(constraints = "current_days >= 0")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Streak {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(name = "current_days", nullable = false)
  private Integer currentDays;

  @Column(name = "last_completed_date", nullable = true)
  private LocalDate lastCompletedDate;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PreUpdate
  void touch() {
    updatedAt = LocalDateTime.now();
  }

  @PrePersist
  void applyDefaults() {
    if (currentDays == null) currentDays = 0;
    updatedAt = LocalDateTime.now();
  }
}
