package com.example.kafkademo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@TestPropertySource("classpath:test.properties")
public class KafkaDemoApplicationTests {

  @Test
  public void contextLoads() {
  }

}
