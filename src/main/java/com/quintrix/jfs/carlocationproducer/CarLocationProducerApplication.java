package com.quintrix.jfs.carlocationproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootApplication
public class CarLocationProducerApplication implements ApplicationRunner {

  @Autowired
  KafkaTemplate kafkaTemplate;

  public static void main(String[] args) {
    SpringApplication.run(CarLocationProducerApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // TODO Auto-generated method stub
    String message = "Hello, World";
    ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send("car-locations", message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("Sent message=[" + message + "] with offset=["
            + result.getRecordMetadata().offset() + "]");
      }

      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
      }
    });
  }

}
