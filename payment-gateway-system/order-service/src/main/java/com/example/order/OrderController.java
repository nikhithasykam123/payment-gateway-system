package com.example.order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderServiceBean service;
  public OrderController(OrderServiceBean s){ this.service = s; }

  @PostMapping
  public ResponseEntity<OrderEntity> create(@RequestBody OrderEntity o){ return ResponseEntity.ok(service.create(o)); }

  @GetMapping("/{id}")
  public ResponseEntity<OrderEntity> get(@PathVariable Long id){ return ResponseEntity.ok(service.get(id)); }

  @PutMapping("/{id}/status")
  public ResponseEntity<OrderEntity> status(@PathVariable Long id, @RequestParam OrderStatus value){
    return ResponseEntity.ok(service.setStatus(id, value));
  }
}
