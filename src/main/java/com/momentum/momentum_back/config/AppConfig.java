package com.momentum.momentum_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.momentum.momentum_back.entity.User;
import com.momentum.momentum_back.exceptions.UnauthorizedException;
import com.momentum.momentum_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      final User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

      return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .build();
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider(
      UserDetailsService uds, PasswordEncoder encoder
  ) {
    var provider = new DaoAuthenticationProvider(uds);
    provider.setPasswordEncoder(encoder);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
