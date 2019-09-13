package com.example.kafkademo.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = {"some-topic"})
@TestPropertySource("classpath:test.properties")
public class ProducerTest {

  @Autowired
  private KafkaTemplate<String, Message> kafkaTemplate;
  @Autowired
  private ConsumerFactory consumerFactory;

  @Test
  public void sendMessage() {
    Producer producer = new Producer("some-topic", kafkaTemplate);
    producer.sendMessage(new Message("some-message", LocalDateTime.of(1, 1, 1, 1, 1)));

    Consumer<String, Message> consumer = consumerFactory.createConsumer("some-group");
    consumer.subscribe(Collections.singletonList("some-topic"));
    ConsumerRecord<String, Message> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "some-topic");

    assertThat(singleRecord).isNotNull();
    assertThat(singleRecord.value().getText()).isEqualTo("some-message");
    assertThat(singleRecord.value().getCreatedDate()).isEqualTo(LocalDateTime.of(1, 1, 1, 1, 1));
  }

}