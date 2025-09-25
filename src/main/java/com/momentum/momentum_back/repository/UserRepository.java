package com.momentum.momentum_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momentum.momentum_back.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Boolean existsByEmail(String email);

}
