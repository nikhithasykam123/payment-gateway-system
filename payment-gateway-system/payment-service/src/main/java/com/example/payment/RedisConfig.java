package com.example.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
  @Bean
  public LettuceConnectionFactory redisConnectionFactory(){
    return new LettuceConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory cf){
    RedisTemplate<String, String> rt = new RedisTemplate<>();
    rt.setConnectionFactory(cf);
    return rt;
  }
}
