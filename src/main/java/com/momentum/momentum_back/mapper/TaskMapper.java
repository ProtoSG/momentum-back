package com.momentum.momentum_back.mapper;

import com.momentum.momentum_back.dto.TaskDTO;
import com.momentum.momentum_back.entity.Task;
import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

  private final UserRepository userRepository;

  public TaskDTO toDto(Task task) {
    if (task == null) {
      return null;
    }
    return new TaskDTO(
      task.getTaskId(),
      task.getUser().getUserId(),
      task.getDescription(),
      task.getStatus(),
      task.getDueDate(),
      task.getPriority()
    );
  }

  public Task toEntity(TaskDTO taskDto) {
    if (taskDto == null) {
        return null;
    }
    Task task = new Task();
    User user = userRepository.findById(taskDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + taskDto.getUserId()));

    task.setTaskId(taskDto.getTaskId());
    task.setUser(user);
    task.setDescription(taskDto.getDescription());
    task.setStatus(taskDto.getStatus());
    task.setDueDate(taskDto.getDueDate());
    task.setPriority(taskDto.getPriority());

    return task;
  }
}
