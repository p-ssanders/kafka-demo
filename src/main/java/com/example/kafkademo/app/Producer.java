package com.example.kafkademo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class Producer {

  private static final Logger logger = LoggerFactory.getLogger(Producer.class);

  private final String topic;
  private final KafkaTemplate<String, Message> kafkaTemplate;

  public Producer(String topic, KafkaTemplate<String, Message> kafkaTemplate) {
    this.topic = topic;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(Message message) {
    this.kafkaTemplate.send(topic, message);

    logger.info(String.format("Produced: %s", message));
  }
}
