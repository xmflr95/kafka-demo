package com.example.demo.kafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


public class KafkaModule<K, V> {
	
	public KafkaModule() {}
	
//	public ProducerFactory<String, String> producerStringFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigProps());
//	}
//	
//	public ProducerFactory<String, Object> producerObjectFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigProps());
//	}
//	
//	public ProducerFactory<String, byte[]> producerByteFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigProps());
//	}
	
	public ProducerFactory<String, V> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigProps());
	}
	
	public KafkaTemplate<String, V> kafkaStringTemplate(ProducerFactory<String, V> pf) {
		return new KafkaTemplate<>(pf);
	}
	
	public KafkaTemplate<String, V> kafkaObjectTemplate(ProducerFactory<String, V> pf) {
		return new KafkaTemplate<>(pf, 
				Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
	}
	
	public KafkaTemplate<String, V> kafkaByteTemplate(ProducerFactory<String, V> pf) {
		return new KafkaTemplate<>(pf, 
				Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class));
	}
	
	public Map<String, Object> producerConfigProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG		, "localhost:9092");
		props.put(ProducerConfig.RETRIES_CONFIG					, 1);
		props.put(ProducerConfig.ACKS_CONFIG					, "all"); // all == -1 
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG	, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG	, StringSerializer.class);		
		return props;
	}
	
	public Map<String, Object> consumerConfigProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG			, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG					, "group_test");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG			, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG			, false); // ??????????????? ?????? (enable.auto.commit = true => ???????????? ?????? ?????? ??????, ??????/????????? ???????????? ??????)
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG		, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG	, StringDeserializer.class);		
		return props;
	}
	
	/**
	 * createSimpleTopic (?????? ?????? ??????)
	 * ?????? ????????? ???????????? ?????? ????????? ?????? ??????
	 * @param topicName : String
	 * @return new topic : NewTopic
	 */
	public NewTopic createTopic(String topicName) {
		NewTopic topic = TopicBuilder.name(topicName)
				.build();
		
		return topic;
	}
}
