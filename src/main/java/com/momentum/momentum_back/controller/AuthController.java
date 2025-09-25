package com.momentum.momentum_back.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentum.momentum_back.dto.AuthRequestDTO;
import com.momentum.momentum_back.dto.AuthResponseDTO;
import com.momentum.momentum_back.dto.UserCreateDTO;
import com.momentum.momentum_back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private static final String REFRESH_TOKEN = "refresh_token";

  @PostMapping("/login")
  public ResponseEntity<?> login(
    @RequestBody @Valid AuthRequestDTO requestDTO
  ) {
    AuthResponseDTO responseDTO = authService.login(requestDTO);

    ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_TOKEN, responseDTO.getRefreshToken())
      .httpOnly(true)
      .secure(false)
      .sameSite("Lax")
      .path("/auth/refresh")
      .maxAge(Duration.ofDays(7))
      .build();

    return ResponseEntity.status(HttpStatus.OK)
      .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
      .body(responseDTO);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody @Valid UserCreateDTO userCreateDTO
  ) {
    AuthResponseDTO responseDTO = authService.register(userCreateDTO);

    ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_TOKEN, responseDTO.getRefreshToken())
      .httpOnly(true)
      .secure(false)
      .sameSite("Lax")
      .path("/auth/refresh")
      .maxAge(Duration.ofDays(7))
      .build();

    return ResponseEntity.status(HttpStatus.CREATED)
      .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
      .body(responseDTO);
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(
      @CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken
  ) {
    AuthResponseDTO responseDTO = authService.refreshToken(refreshToken);

    ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_TOKEN, responseDTO.getRefreshToken())
      .httpOnly(true)
      .secure(false)
      .sameSite("Lax")
      .path("/auth/refresh")
      .maxAge(Duration.ofDays(7))
      .build();

    return ResponseEntity.status(HttpStatus.OK)
      .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
      .body(responseDTO);
  }
}
