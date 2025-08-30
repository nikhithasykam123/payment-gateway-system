package com.example.txn;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long orderId;
  private Double amount;
  private String status;
  private Instant createdAt;
}
