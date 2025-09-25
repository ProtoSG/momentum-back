package com.momentum.momentum_back.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping
  public ResponseEntity<?> healthCheck() {
    Map<String, String> message = new HashMap<>();
    message.put("message", "Server active");
    message.put("status", "ok");

    return ResponseEntity.ok(message); 
  }
  
}
