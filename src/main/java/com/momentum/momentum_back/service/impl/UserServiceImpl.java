package com.momentum.momentum_back.service.impl;

import com.momentum.momentum_back.mapper.UserMapper;
import org.springframework.stereotype.Service;

import com.momentum.momentum_back.dto.UserDTO;
import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.exceptions.ResourceNotFoundException;
import com.momentum.momentum_back.repository.UserRepository;
import com.momentum.momentum_back.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDTO getUserById(Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

    return userMapper.toDto(user);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

}
