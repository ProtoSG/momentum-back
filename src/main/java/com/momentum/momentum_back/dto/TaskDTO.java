package com.momentum.momentum_back.dto;

import java.time.LocalDate;

import com.momentum.momentum_back.entity.TaskPriority;
import com.momentum.momentum_back.entity.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

  private Long taskId;

  private Long userId;
  
  @NotBlank(message = "La descripción no puede estar vacío.")
  @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
  private String description;

  @NotNull(message = "El estado es obligatorio.")
  private TaskStatus status;

  private LocalDate dueDate;

  @NotNull(message = "El estado es obligatorio.")
  private TaskPriority priority;
}
