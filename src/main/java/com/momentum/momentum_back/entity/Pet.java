package com.momentum.momentum_back.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pet_id")
  private Long petId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  private User user;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "level", referencedColumnName = "level", nullable = false)
  private PetLevel level;

  @Column(name = "points_total", nullable = false)
  private Integer pointsTotal;

  @Column(name = "experience", nullable = false)
  private Integer experience;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "health", nullable = false)
  private Integer health;

  @Column(name = "energy", nullable = false)
  private Integer energy;

  @Column(name = "hunger", nullable = false)
  private Integer hunger;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  void applyDefaults() {
    if (url == null) url = "https://ibb.co/60tcMVfs";
    if (pointsTotal == null) pointsTotal = 0;
    if (experience == null) experience = 0;
    if (health == null) health = 100;
    if (energy == null) energy = 100;
    if (hunger == null) hunger = 100;
  }

}
