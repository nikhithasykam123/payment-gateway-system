package com.example.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserRepository repo;
  private final JwtUtil jwtUtil;
  public AuthController(UserRepository repo, JwtUtil jwtUtil){ this.repo = repo; this.jwtUtil = jwtUtil; }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body){
    String email = body.get("email");
    // Dummy auth: if user exists, issue token (no password for scaffold)
    var user = repo.findAll().stream().filter(u -> email.equals(u.getEmail())).findFirst();
    if(user.isPresent()){
      String token = jwtUtil.generateToken(user.get().getId().toString());
      return ResponseEntity.ok(Map.of("token", token));
    }
    return ResponseEntity.status(401).body("Invalid credentials");
  }
}
