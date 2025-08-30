package com.example.order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceBean {
  private final OrderRepository repo;
  public OrderServiceBean(OrderRepository repo){ this.repo = repo; }

  public OrderEntity create(OrderEntity o){
    o.setStatus(OrderStatus.PENDING);
    return repo.save(o);
  }
  public OrderEntity get(Long id){ return repo.findById(id).orElseThrow(); }
  public OrderEntity setStatus(Long id, OrderStatus s){
    OrderEntity o = get(id); o.setStatus(s); return repo.save(o);
  }
}
