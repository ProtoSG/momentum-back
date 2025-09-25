package com.momentum.momentum_back.service;

import com.momentum.momentum_back.dto.UserDTO;
import com.momentum.momentum_back.entity.User;

public interface UserService {

  UserDTO getUserById(Long userId);

  User getUserByEmail(String email);
}
