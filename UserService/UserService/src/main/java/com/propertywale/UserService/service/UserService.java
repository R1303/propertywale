package com.propertywale.UserService.service;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.propertywale.UserService.config.UserServiceConfig;
import com.propertywale.UserService.dto.UserDto;

@Service
@EnableDiscoveryClient
public class UserService {

	@Value("${user.service.topic}")
	private String topic;

	@Value("${consumer.service.url}")
	private String consumerServiceUrl;

	private static final String CACHE_PREFIX = "USER_";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private UserServiceConfig userServiceConfig;

	@Autowired
	private RestTemplate restTemplate;

	public void saveUserData(UserDto userDto) {
		userServiceConfig.sendMessage(topic, userDto);
	}

	public UserDto getUserById(Long id) {
		String cacheKey = CACHE_PREFIX + id;
		UserDto cachedUser = (UserDto) redisTemplate.opsForValue().get(cacheKey);
		if (cachedUser != null) {
			System.out.println("Cache hit for User ID: " + id);
			return cachedUser;
		}
		String url = consumerServiceUrl + "getUser/" + id;
		try {
			UserDto user = restTemplate.getForObject(url, UserDto.class);
			redisTemplate.opsForValue().set(cacheKey, user, 1, TimeUnit.MINUTES);
			return user;
		} catch (Exception e) {
			throw new NoSuchElementException("No User Found for this id : " + id);
		}
	}
}
