package com.momentum.momentum_back.service;

import com.momentum.momentum_back.dto.AuthRequestDTO;
import com.momentum.momentum_back.dto.AuthResponseDTO;
import com.momentum.momentum_back.dto.UserCreateDTO;

public interface AuthService {

  AuthResponseDTO login(AuthRequestDTO authRequestDTO);

  AuthResponseDTO register(UserCreateDTO userCreateDTO);

  AuthResponseDTO refreshToken(final String refreshToken);
}
