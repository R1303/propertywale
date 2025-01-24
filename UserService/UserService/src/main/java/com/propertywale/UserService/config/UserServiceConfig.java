package com.propertywale.UserService.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.client.RestTemplate;

import com.propertywale.UserService.dto.UserDto;

@Configuration
public class UserServiceConfig {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public String sendMessage(String topic, UserDto user) {
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, user);
		try {
			@SuppressWarnings("static-access")
			CompletableFuture<Object> completeFuture = future.completedFuture(future.get().getProducerRecord().value());
			System.out.println("Producer : " + completeFuture.get());
			return future.get().getProducerRecord().value().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Use String serializer for keys
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Use JSON serializer for values
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}
