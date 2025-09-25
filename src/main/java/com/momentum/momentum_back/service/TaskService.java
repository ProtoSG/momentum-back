package com.momentum.momentum_back.service;

import java.util.List;

import com.momentum.momentum_back.dto.TaskDTO;
import com.momentum.momentum_back.entity.TaskStatus;

public interface TaskService {

  List<TaskDTO> getTaskByEmail(String email);

  TaskDTO createTask(TaskDTO taskDTO, String email);

  TaskDTO readTaskById(Long taskId);

  TaskDTO updateTaskById(Long taskId, TaskDTO taskDTO, String email);

  TaskDTO updateStatusTaskById(Long taskId, TaskStatus status, String email);

  void deleteTaskById(Long taskId);
}
