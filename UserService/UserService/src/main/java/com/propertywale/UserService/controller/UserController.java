package com.propertywale.UserService.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertywale.UserService.dto.LoginDto;
import com.propertywale.UserService.dto.UserDto;
import com.propertywale.UserService.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto userDto) {
		userService.saveUserData(userDto);
		return ResponseEntity.ok("User Registered");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok("Login Successful");
	}
	
	@GetMapping("/getUser/{userId}")
	public UserDto getUserById(@PathVariable(name = "userId") String userId) {
		return userService.getUserById(Long.parseLong(userId));
	}
	
	@PostMapping("/validate")
    public Map<String, Object> validateUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("user".equals(username) && "password".equals(password)) {
            return Map.of("valid", true, "username", username);
        }
        return Map.of("valid", false);
    }
}
