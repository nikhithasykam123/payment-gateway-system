package com.example.txn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
  List<TransactionEntity> findByUserId(Long userId);
}
