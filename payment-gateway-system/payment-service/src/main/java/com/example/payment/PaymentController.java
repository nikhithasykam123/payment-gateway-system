package com.example.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;

@RestController
@RequestMapping("/payments")
public class PaymentController {
  private final UserClient userClient;
  private final OrderClient orderClient;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final RedisTemplate<String, String> redisTemplate;

  public PaymentController(UserClient userClient, OrderClient orderClient, KafkaTemplate<String, String> kafkaTemplate,
                           RedisTemplate<String, String> redisTemplate){
    this.userClient = userClient; this.orderClient = orderClient; this.kafkaTemplate = kafkaTemplate; this.redisTemplate = redisTemplate;
  }

  @PostMapping("/pay")
  public ResponseEntity<String> pay(@RequestBody PaymentRequest req){
    try{
      String res = userClient.deduct(req.getUserId(), req.getAmount());
      orderClient.setStatus(req.getOrderId(), "PAID");
      kafkaTemplate.send("payment-events", "SUCCESS user=" + req.getUserId() + " order=" + req.getOrderId());
      // Publish an SMS event to Redis channel 'sms' (simulated SMS)
      String sms = "SMS: Payment successful for user=" + req.getUserId() + " order=" + req.getOrderId();
      redisTemplate.convertAndSend("sms", sms);
      return ResponseEntity.ok("Payment success: " + res);
    }catch(Exception ex){
      orderClient.setStatus(req.getOrderId(), "FAILED");
      kafkaTemplate.send("payment-events", "FAILED user=" + req.getUserId() + " order=" + req.getOrderId());
      String sms = "SMS: Payment FAILED for user=" + req.getUserId() + " order=" + req.getOrderId();
      redisTemplate.convertAndSend("sms", sms);
      return ResponseEntity.badRequest().body("Payment failed: " + ex.getMessage());
    }
  }
}
