package com.momentum.momentum_back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

  @NotBlank(message = "El nombre el obligatoria")
  private String name;
  
  @Email(message = "Debe ser un email válido")
  private String email;

  @NotNull(message = "La contraseña es obligatoria")
  private String password;

  private String timezone;
  
  private String locale;
  
  @Min(value = 0, message = "La hora de inicio debe ser entre 0 y 23")
  @Max(value = 23, message = "La hora de inicio debe ser entre 0 y 23")
  private Integer dayStartHour;
}
