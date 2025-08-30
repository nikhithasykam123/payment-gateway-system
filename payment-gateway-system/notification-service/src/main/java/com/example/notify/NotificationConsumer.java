package com.example.notify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

  @KafkaListener(topics = "payment-events", groupId = "notifications")
  public void listen(ConsumerRecord<String, String> record){
    System.out.println("Notification received: " + record.value());
  }
}
