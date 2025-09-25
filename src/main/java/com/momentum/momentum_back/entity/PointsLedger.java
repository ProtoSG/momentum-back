package com.momentum.momentum_back.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "points_ledger")
@Check(constraints = "amount <> 0")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointsLedger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ledger_id")
  private Long ledgerId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
  private Pet pet;

  @Column(name = "amount", nullable = false)
  private Integer amount;

  @Column(name = "reason", nullable = false)
  private String reason;

  @Column(name = "ref_type", nullable = true)
  private String refType;

  @Column(name = "ref_id", nullable = true)
  private Long ref_id;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

}
