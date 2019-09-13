package com.example.kafkademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SpringBootApplication
public class KafkaDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaDemoApplication.class, args);
  }

  @Configuration
  public static class KafkaDemoApplicationConfiguration {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
        ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
        ConsumerFactory<Object, Object> consumerFactory
      , KafkaTemplate<Object, Object> template
    ) {

      ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
      configurer.configure(factory, consumerFactory);

      factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), 2));

      return factory;
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory(Serializer<Object> keySerializer,
        Serializer<Object> valueSerializer) {
      Map<String, Object> props = new HashMap<>();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

      return new DefaultKafkaProducerFactory<>(props, keySerializer, valueSerializer);
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory(Deserializer<Object> keyDeserializer,
        Deserializer<Object> valueDeserializer) {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "users_group");
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

      return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer);
    }

    @Bean
    public Serializer keySerializer() {
      return new StringSerializer();
    }

    @Bean
    public Serializer valueSerializer(ObjectMapper objectMapper) {
      return new JsonSerializer(objectMapper);
    }

    @Bean
    public Deserializer keyDeserializer() {
      return new ErrorHandlingDeserializer2<>(new StringDeserializer());
    }

    @Bean
    public Deserializer valueDeserializer(ObjectMapper objectMapper) {
      JsonDeserializer<Object> valueJsonDeserializer = new JsonDeserializer<>(objectMapper);
      valueJsonDeserializer.addTrustedPackages("com.example.kafkademo.app");

      return new ErrorHandlingDeserializer2<>(valueJsonDeserializer);
    }

    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      return objectMapper;
    }

  }
}
