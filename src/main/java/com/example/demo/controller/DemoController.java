package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService;
import com.example.demo.vo.User;

@RestController
@RequestMapping("/api/v1")
public class DemoController {
	
	private Environment env;
	private DemoService demoService;
	private KafkaTemplate<String, String> stringTemplate;
	private KafkaTemplate<String, byte[]> byteTemplate;
	private KafkaTemplate<String, User> userTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public DemoController(Environment env, DemoService demoService, 
							KafkaTemplate<String, String> stringTemplate,
							KafkaTemplate<String, byte[]> byteTemplate, 
							KafkaTemplate<String, User> userTemplate) {
		this.env = env;
		this.demoService = demoService;
		this.stringTemplate = stringTemplate;
		this.byteTemplate = byteTemplate;
		this.userTemplate = userTemplate;
	}
	
	@GetMapping("/status")
	public String status() {		
		if (logger.isInfoEnabled()) {
			logger.info("Connect GET /status");
			logger.info("server.port : " + env.getProperty("server.port")
					+ "\nlocal.server.port : " + env.getProperty("local.server.port")
					+ "\nTest INFO LOG");
			logger.warn("TEST WARN LOG");
			logger.error("TEST ERR LOG");
		}
		return String.format("Hello DemoController, this sever started on PORT : " + env.getProperty("server.port")
				+ ", local server PORT : " + env.getProperty("local.server.port"));
	}
	
	@GetMapping("/err")
	public String errtest() throws IOException {
		String result = "Error Test";
		
		BufferedReader br;
		
		br = new BufferedReader(new FileReader("nothing"));
		br.readLine();
		br.close();
		
		
		return result;
	}
	
	@PostMapping("/put")
	public HashMap<String, Object> putItem(@RequestBody HashMap<String, Object> item) {	
		
		logger.info("Request Item : " + item);
		
		for (Map.Entry<String, Object> entry : item.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			logger.info("key : " + key);
			logger.info("value : " + value);
		}
		
		return item;
	}
	
	@PostMapping("/kafka/test")
	public void topicBuild(@RequestBody HashMap<String, Object> data) {
		logger.info("data :: " + data);
		
		String topic = "test";
		
		stringTemplate.send(topic, "string message");
		byteTemplate.send(topic, "byte Message".getBytes());
		
		User user = new User();
		user.setName(data.get("name").toString());
		user.setAge(Integer.parseInt(data.get("age").toString()));
		user.setHobby(data.get("hobby").toString());
		
		userTemplate.send(topic, user);
	}
	
}
