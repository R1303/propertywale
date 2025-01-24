package com.propertywale.PropertyService.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.propertywale.PropertyService.dto.PropertyDto;


@Configuration
public class PropertyServiceConfig {
	
	@Autowired
	private KafkaTemplate<String,Object>kafkaTemplate;
	
	public String sendMessage(String topic, PropertyDto property) {
		CompletableFuture<SendResult<String,Object>> future=  kafkaTemplate.send(topic, property);
		try {
			@SuppressWarnings("static-access")
			CompletableFuture<Object> completeFuture = future.completedFuture(future.get().getProducerRecord().value());
			System.out.println("Producer : "+completeFuture.get());
			return future.get().getProducerRecord().value().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
