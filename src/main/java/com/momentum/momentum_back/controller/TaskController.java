package com.momentum.momentum_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentum.momentum_back.dto.TaskDTO;
import com.momentum.momentum_back.dto.TaskStatusUpdateDTO;
import com.momentum.momentum_back.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
  
  private final TaskService taskService;

  @GetMapping()
  public ResponseEntity<?> getTasksByUser() {

    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    List<TaskDTO> tasks = taskService.getTaskByEmail(userEmail);

    return ResponseEntity.status(HttpStatus.OK)
      .body(tasks);
  }

  @PostMapping
  public ResponseEntity<?> postTask(
      @RequestBody @Valid  TaskDTO taskDTO
  ) {
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    TaskDTO taskCreated = taskService.createTask(taskDTO, userEmail);

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(taskCreated);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateTask(
    @PathVariable Long id,
    @RequestBody @Valid TaskDTO taskDTO
  ) {
    
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    TaskDTO taskUpadted = taskService.updateTaskById(id, taskDTO, userEmail);

    return ResponseEntity.status(HttpStatus.OK)
      .body(taskUpadted);
  }

  @PutMapping("/state/{id}")
  public ResponseEntity<?> updateStateTask(
    @PathVariable Long id,
    @RequestBody @Valid TaskStatusUpdateDTO statusUpdate
  ) {
    
    String userEmail = SecurityContextHolder.getContext()
      .getAuthentication()
      .getName();

    TaskDTO taskUpdated = taskService.updateStatusTaskById(id, statusUpdate.getStatus(), userEmail);

    return ResponseEntity.status(HttpStatus.OK)
      .body(taskUpdated);

  }
}
