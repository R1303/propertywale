package com.propertywale.ConsumerService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.propertywale.ConsumerService.entity.PropertyEntity;
import com.propertywale.ConsumerService.entity.UserEntity;
import com.propertywale.ConsumerService.repository.PropertyRepository;
import com.propertywale.ConsumerService.repository.UserRepository;
import com.propertywale.PropertyService.dto.PropertyDto;
import com.propertywale.UserService.dto.UserDto;

@Service
public class ConsumerService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	
	@KafkaListener(topics = "${user.service.topic}")
	public void saveUserData(UserDto userDto) {
		UserEntity userEntity=new UserEntity(userDto.username(),userDto.password(),userDto.email());
		userRepository.save(userEntity);
	}
	
	@KafkaListener(topics = "${property.service.topic}")
	public void savePropertyData(PropertyDto propertyDto) {
		PropertyEntity propertyEntity=new PropertyEntity(propertyDto.title(),propertyDto.description()
				,propertyDto.price(),propertyDto.location());
		propertyRepository.save(propertyEntity);
	}
}
