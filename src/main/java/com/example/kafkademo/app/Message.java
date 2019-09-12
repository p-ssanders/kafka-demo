package com.example.kafkademo.app;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {

  private String text;
  private LocalDateTime createdDate;

  public Message() {
  }

  public Message(String text, LocalDateTime createdDate) {
    this.text = text;
    this.createdDate = createdDate;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "Message{" +
        "text='" + text + '\'' +
        ", createdDate=" + createdDate +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Message)) {
      return false;
    }
    Message message = (Message) o;
    return Objects.equals(text, message.text) &&
        Objects.equals(createdDate, message.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, createdDate);
  }
}
