package com.momentum.momentum_back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSetting {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(name = "timezone", nullable = false)
  private String timezone;

  @Column(name = "day_start_hour", nullable = false)
  private Integer dayStartHour;

  @Column(name = "locale")
  private String locale;

  @PrePersist
  void applyDefaults() {
    if (timezone == null) timezone = "UTC";
    if (dayStartHour == null) dayStartHour = 5;
    if (locale == null) locale = "es";
  }
}
