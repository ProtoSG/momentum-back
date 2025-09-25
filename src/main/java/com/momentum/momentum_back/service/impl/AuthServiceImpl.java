package com.momentum.momentum_back.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.momentum.momentum_back.dto.AuthRequestDTO;
import com.momentum.momentum_back.dto.AuthResponseDTO;
import com.momentum.momentum_back.dto.UserCreateDTO;
import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.entity.UserSetting;
import com.momentum.momentum_back.exceptions.ResourceConflictException;
import com.momentum.momentum_back.exceptions.ResourceNotFoundException;
import com.momentum.momentum_back.exceptions.UnauthorizedException;
import com.momentum.momentum_back.repository.UserRepository;
import com.momentum.momentum_back.repository.UserSettingRepository;
import com.momentum.momentum_back.service.AuthService;
import com.momentum.momentum_back.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserSettingRepository userSettingRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          authRequestDTO.getEmail(), 
          authRequestDTO.getPassword()
        )
      );

    User user = userRepository.findByEmail(authRequestDTO.getEmail())
      .orElseThrow();

    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return AuthResponseDTO.builder()
      .email(user.getEmail())
      .token(jwtToken)
      .refreshToken(refreshToken)
      .build();
  }

  public AuthResponseDTO register(UserCreateDTO userCreateDTO) {

    if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
      throw new ResourceConflictException("Usuario con email: '" + userCreateDTO.getEmail() + "' ya existe");
    }

    User user = User.builder()
      .name(userCreateDTO.getName())
      .email(userCreateDTO.getEmail())
      .password(passwordEncoder.encode(userCreateDTO.getPassword()))
      .build();

    User savedUser = userRepository.save(user);

    UserSetting userSetting = UserSetting.builder()
      .user(savedUser)
      .timezone(userCreateDTO.getTimezone()) 
      .locale(userCreateDTO.getLocale())
      .dayStartHour(userCreateDTO.getDayStartHour())
      .build();
    
    userSettingRepository.save(userSetting);

    String jwtToken = jwtService.generateToken(savedUser);
    String refreshToken = jwtService.generateRefreshToken(savedUser);

    AuthResponseDTO responseDTO = AuthResponseDTO.builder()
      .email(userCreateDTO.getEmail())
      .token(jwtToken)
      .refreshToken(refreshToken)
      .build();

    return responseDTO;
  }

  public AuthResponseDTO refreshToken(final String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new UnauthorizedException("Invalid cookie token");
    }

    final String userEmail = jwtService.extractUsername(refreshToken);

    if(userEmail == null) {
      throw new UnauthorizedException("Invalid refresh token");
    }

    final User user = userRepository.findByEmail(userEmail)
      .orElseThrow(() -> new ResourceNotFoundException("Usuario con email: '" + userEmail + "' no existe"));

    if (!jwtService.isTokenValid(refreshToken, user)) {
      throw new UnauthorizedException("Invalid refresh token");
    }

    final String accessToken = jwtService.generateToken(user);
    final String newRefreshToken = jwtService.generateRefreshToken(user);

    return AuthResponseDTO.builder()
      .email(user.getEmail())
      .token(accessToken)
      .refreshToken(newRefreshToken)
      .build();
  }

}
