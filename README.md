# spring-kafka-demo

Spring Boot application that demonstrates simple publish/subscribe functionality with Spring Kafka.

### Dependencies

* [Confluent Platform](https://www.confluent.io/download/)

### Build
```bash
./mvnw clean install
```

### Run
```bash
confluent local start
./mvnw spring-boot:run
```

### Execute
```
curl -X POST -F 'message=test' http://localhost:9000/kafka/publish
curl -X POST -F 'message=foo' http://localhost:9000/kafka/publish
```
