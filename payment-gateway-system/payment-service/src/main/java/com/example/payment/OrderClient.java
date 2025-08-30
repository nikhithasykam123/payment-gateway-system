package com.example.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service")
public interface OrderClient {
  @PutMapping("/orders/{id}/status")
  Object setStatus(@PathVariable("id") Long id, @RequestParam("value") String value);
}
