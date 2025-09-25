package com.momentum.momentum_back.service.impl;

import java.util.List;

import com.momentum.momentum_back.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import com.momentum.momentum_back.dto.TaskDTO;
import com.momentum.momentum_back.entity.Task;
import com.momentum.momentum_back.entity.TaskStatus;
import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.repository.TaskRepository;
import com.momentum.momentum_back.repository.UserRepository;
import com.momentum.momentum_back.service.TaskService;
import com.momentum.momentum_back.exceptions.ResourceNotFoundException;
import com.momentum.momentum_back.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TaskMapper taskMapper;

  public List<TaskDTO> getTaskByEmail(String email) {
    if (userRepository.findByEmail(email).isEmpty()) {
      throw new ResourceNotFoundException("User not found");
    }

    List<Task> tasks = taskRepository.findByUserEmail(email);

    return tasks.stream()
      .map(taskMapper::toDto)
      .toList();
  }

  public TaskDTO createTask(TaskDTO taskDTO, String email) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Task newTask = Task.builder()
      .user(user)
      .description(taskDTO.getDescription())
      .status(taskDTO.getStatus())
      .dueDate(taskDTO.getDueDate())
      .priority(taskDTO.getPriority())
      .build();

    Task taskSaved = taskRepository.save(newTask);

    return taskMapper.toDto(taskSaved);
  }

  public TaskDTO readTaskById(Long taskId) {
    Task task = taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    return taskMapper.toDto(task);
  }

  public TaskDTO updateTaskById(Long taskId, TaskDTO taskDTO, String email) {
    Task taskExist = taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    if (!taskExist.getUser().getEmail().equals(email)) {
      throw new UnauthorizedException("No autorizado");
    }

    taskExist.setDescription(taskDTO.getDescription());
    taskExist.setStatus(taskDTO.getStatus());
    taskExist.setDueDate(taskDTO.getDueDate());
    taskExist.setPriority(taskDTO.getPriority());

    Task taskSaver = taskRepository.save(taskExist);
    return taskMapper.toDto(taskSaver);
  }

  public TaskDTO updateStatusTaskById(Long taskId, TaskStatus status, String email) {
    Task taskExist = taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    if (!taskExist.getUser().getEmail().equals(email)) {
      throw new UnauthorizedException("No autorizado");
    }

    taskExist.setStatus(status);

    Task taskSaved = taskRepository.save(taskExist);

    return taskMapper.toDto(taskSaved);
  }

  public void deleteTaskById(Long taskId) {
    if( taskRepository.findById(taskId).isEmpty() ) {
      throw new ResourceNotFoundException("Task not found");
    }

    taskRepository.deleteById(taskId);
  }

}
