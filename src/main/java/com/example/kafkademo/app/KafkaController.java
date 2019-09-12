package com.example.kafkademo.app;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  private final Producer producer;

  public KafkaController(Producer producer) {
    this.producer = producer;
  }

  @PostMapping("/publish")
  public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
    this.producer.sendMessage(new Message(message, LocalDateTime.now()));
  }
}
