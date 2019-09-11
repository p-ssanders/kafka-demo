package com.example.kafkademo.app;

import java.util.Objects;

public class Message {

  private String text;

  public Message() {
  }

  public Message(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "Message{" +
        "text='" + text + '\'' +
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
    return Objects.equals(text, message.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
