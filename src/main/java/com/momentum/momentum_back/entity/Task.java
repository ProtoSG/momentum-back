package com.momentum.momentum_back.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Check(constraints = "points_value >= 0")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long taskId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  private User user;

  @Column(name = "description", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TaskStatus status;

  @Column(name = "points_value", nullable = false)
  private Integer pointsValue;

  @Column(name = "due_date", nullable = true)
  private LocalDate dueDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false)
  private TaskPriority priority;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  void applyDefaults() {
    if (priority == null) priority = TaskPriority.LOW;
    if (status == null) status = TaskStatus.TODO;

    if (pointsValue == null) {
      switch (priority) {
        case HIGH -> pointsValue = 20;
        case MEDIUM -> pointsValue = 15;
        default -> pointsValue = 10;
      }
    }
    if (pointsValue < 0 ) throw new IllegalArgumentException("Los puntos no pueden ser negativo");
  }

  @PreUpdate
  void touch() {
    if (pointsValue < 0 ) throw new IllegalArgumentException("Los puntos no pueden ser negativo");
  }

}
