package com.propertywale.PropertyService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.propertywale.PropertyService.config.PropertyServiceConfig;
import com.propertywale.PropertyService.dto.PropertyDto;

@Service
public class PropertyService {

    @Value("${property.service.topic}")
	private String topic;
	
	@Autowired
	private PropertyServiceConfig propertyServiceConfig;
	
	public void saveProperty(PropertyDto propertyDto) {
		propertyServiceConfig.sendMessage(topic, propertyDto);
	}
}
