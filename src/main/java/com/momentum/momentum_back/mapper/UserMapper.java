package com.momentum.momentum_back.mapper;

import com.momentum.momentum_back.dto.UserDTO;
import com.momentum.momentum_back.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  
  public UserDTO toDto(User user) {
    if (user == null) {
      return null;
    }
    return new UserDTO(
      user.getUserId(),
      user.getName(),
      user.getEmail()
    );
  }

  public User toEntity(UserDTO userDto) {
    if (userDto == null) {
      return null;
    }
    return User.builder()
              .userId(userDto.getUserId())
              .name(userDto.getName())
              .email(userDto.getEmail())
              .build();
  }
}
