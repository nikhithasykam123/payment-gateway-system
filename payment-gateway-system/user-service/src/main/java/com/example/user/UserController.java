package com.example.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService service;
  public UserController(UserService service){ this.service = service; }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User u){ return ResponseEntity.ok(service.create(u)); }

  @GetMapping("/{id}")
  public ResponseEntity<User> get(@PathVariable Long id){ return ResponseEntity.ok(service.get(id)); }

  @PutMapping("/{id}/wallet/add")
  public ResponseEntity<String> add(@PathVariable Long id, @RequestParam double amount){
    service.add(id, amount); return ResponseEntity.ok("Added");
  }

  @PutMapping("/{id}/wallet/deduct")
  public ResponseEntity<String> deduct(@PathVariable Long id, @RequestParam double amount){
    return service.deduct(id, amount) ? ResponseEntity.ok("Deducted") :
           ResponseEntity.badRequest().body("Insufficient balance");
  }
}
