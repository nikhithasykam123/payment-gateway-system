package com.example.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserClient {
  @PutMapping("/users/{id}/wallet/deduct")
  String deduct(@PathVariable("id") Long id, @RequestParam("amount") double amount);
}
