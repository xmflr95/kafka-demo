package com.example.demo.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.demo.vo.User;

@Configuration
public class KafkaConfig {
	/**
   * Kafka Producer 템플릿 설정(전달 받는 Object 타입에 따라 구분)
   * 이부분 분기를 다른 곳에서 처리해서 하나만 설정하는 방법이 없을까...?
   */
	@Bean
	KafkaModule<String, String> kafkaStringModule() {
		KafkaModule<String, String> kafkaModule = new KafkaModule<String, String>();
		return kafkaModule;
	}
	
	@Bean
	KafkaModule<String, byte[]> kafkaByteModule() {
		KafkaModule<String, byte[]> kafkaModule = new KafkaModule<String, byte[]>();
		return kafkaModule;
	}
	
	@Bean
	KafkaModule<String, User> kafkaUserModule() {
		KafkaModule<String, User> kafkaModule = new KafkaModule<String, User>();
		return kafkaModule;
	}
	
	@Bean
	KafkaTemplate<String, String> stringTemplate() {
		ProducerFactory<String, String> producerFactory = kafkaStringModule().producerFactory();
		return kafkaStringModule().kafkaStringTemplate(producerFactory);
	}
	
	@Bean
	KafkaTemplate<String, byte[]> byteTemplate() {
		ProducerFactory<String, byte[]> producerFactory = kafkaByteModule().producerFactory();
		return kafkaByteModule().kafkaByteTemplate(producerFactory);
	}
	
	@Bean
	KafkaTemplate<String, User> userTemplate() {
		ProducerFactory<String, User> producerFactory = kafkaUserModule().producerFactory();
		return kafkaUserModule().kafkaObjectTemplate(producerFactory);
	}
}
