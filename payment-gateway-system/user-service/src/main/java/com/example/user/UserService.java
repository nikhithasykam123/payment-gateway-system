package com.example.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository repo;
  public UserService(UserRepository repo){ this.repo = repo; }

  public User create(User u){
    if(u.getWalletBalance() == null) u.setWalletBalance(0.0);
    return repo.save(u);
  }

  public User get(Long id){ return repo.findById(id).orElseThrow(); }

  @Transactional
  public boolean add(Long id, double amount){
    User u = get(id);
    u.setWalletBalance(u.getWalletBalance() + amount);
    return true;
  }

  @Transactional
  public boolean deduct(Long id, double amount){
    User u = get(id);
    if(u.getWalletBalance() >= amount){
      u.setWalletBalance(u.getWalletBalance() - amount);
      return true;
    }
    return false;
  }
}
