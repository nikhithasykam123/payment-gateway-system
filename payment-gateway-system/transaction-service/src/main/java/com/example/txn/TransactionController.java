package com.example.txn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
  private final TransactionRepository repo;
  public TransactionController(TransactionRepository repo){ this.repo = repo; }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<TransactionEntity>> byUser(@PathVariable Long userId){
    return ResponseEntity.ok(repo.findByUserId(userId));
  }

  @PostMapping
  public ResponseEntity<TransactionEntity> create(@RequestBody TransactionEntity t){
    if(t.getCreatedAt() == null) t.setCreatedAt(Instant.now());
    return ResponseEntity.ok(repo.save(t));
  }
}
