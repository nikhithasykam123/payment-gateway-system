package com.example.order;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "orders")
public class OrderEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Double amount;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
