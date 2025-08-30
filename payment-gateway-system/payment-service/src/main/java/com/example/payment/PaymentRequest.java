package com.example.payment;
import lombok.Data;
@Data
public class PaymentRequest {
  private Long userId;
  private Long orderId;
  private Double amount;
}
