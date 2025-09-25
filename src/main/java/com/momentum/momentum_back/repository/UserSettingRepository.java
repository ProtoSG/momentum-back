package com.momentum.momentum_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momentum.momentum_back.entity.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

  
}
