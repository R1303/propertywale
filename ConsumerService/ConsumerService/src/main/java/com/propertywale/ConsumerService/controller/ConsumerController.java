package com.propertywale.ConsumerService.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertywale.ConsumerService.entity.UserEntity;
import com.propertywale.ConsumerService.repository.UserRepository;
import com.propertywale.UserService.dto.UserDto;

@RestController
@RequestMapping("/api")
public class ConsumerController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/getUser/{userId}")
	public UserDto getUserById(@PathVariable(name = "userId") String userId) {
		UserEntity userEntity = userRepository.findById(Long.parseLong(userId))
				.orElseThrow(NoSuchElementException::new);
		return new UserDto(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail());
	}
}
