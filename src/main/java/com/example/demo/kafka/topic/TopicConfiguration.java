package com.example.demo.kafka.topic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class TopicConfiguration {
	
	private String name;
	private Optional<Integer> partitions;
    private Optional<Short> replicas;
	private Map<String, String> configs;
	private Map<Integer, List<Integer>> replicasAssignments;	
	
}
