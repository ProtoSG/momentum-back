package com.momentum.momentum_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momentum.momentum_back.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByUserEmail(String email);
  
}
