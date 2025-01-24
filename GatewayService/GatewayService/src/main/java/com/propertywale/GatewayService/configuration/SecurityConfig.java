package com.propertywale.GatewayService.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${gateway.service.urls}")
	private String serviceUrls;

	private static final String SECRET = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm";

	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
            .authorizeExchange(auth -> auth
            	.pathMatchers("/login").permitAll()
                .anyExchange().authenticated() // Authenticate all requests
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Optional: JWT-based auth
        return http.build();
    }

//	@Bean
//	public JwtDecoder jwtDecoder() {
//		SecretKey secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
//	
	@Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }
}
