package com.momentum.momentum_back.service;

import com.momentum.momentum_back.entity.User;

public interface JwtService {

  String generateToken(final User user);

  String generateRefreshToken(final User user);

  String extractUsername(final String token);

  boolean isTokenValid(final String token, final User user);
}
