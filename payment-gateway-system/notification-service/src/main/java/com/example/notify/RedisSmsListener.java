package com.example.notify;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisSmsListener implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String payload = new String(message.getBody());
    // Simulate sending SMS by logging. In a real project, integrate with Twilio/MSG91 here.
    System.out.println(">>> [SMS SENT] " + payload);
  }
}
