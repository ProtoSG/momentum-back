package com.momentum.momentum_back.dto;

import com.momentum.momentum_back.entity.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    
  @NotNull(message = "Status is required")
  private TaskStatus status;
    
}
