package com.example.kafkademo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  private static final Logger logger = LoggerFactory.getLogger(Producer.class);
  private static final String TOPIC = "users";

  private final KafkaTemplate<String, Message> kafkaTemplate;

  public Producer(KafkaTemplate<String, Message> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(Message message) {
    this.kafkaTemplate.send(TOPIC, message);

    logger.info(String.format("Produced: %s", message));
  }
}
