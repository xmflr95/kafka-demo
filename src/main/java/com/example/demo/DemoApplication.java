package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DemoApplication {	

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}	
	
	@KafkaListener(topics = "test", groupId = "myId")
	public void sampleConsumer(String message) {
		log.info("sample : " + message);
	}
}
