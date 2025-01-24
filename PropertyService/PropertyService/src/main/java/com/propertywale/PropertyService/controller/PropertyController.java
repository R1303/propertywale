package com.propertywale.PropertyService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertywale.PropertyService.dto.PropertyDto;
import com.propertywale.PropertyService.service.PropertyService;

@RestController
@RequestMapping("/properties")
public class PropertyController {
	
	@Autowired
	private PropertyService propertyService;
	
	@PostMapping
    public ResponseEntity<?> createProperty(@RequestBody PropertyDto propertyDto) {
		propertyService.saveProperty(propertyDto);
        return ResponseEntity.ok("Property Created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id) {
        return ResponseEntity.ok("Property Details");
    }
}
