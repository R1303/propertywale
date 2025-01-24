package com.propertywale.GatewayService.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.propertywale.GatewayService.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
@EnableDiscoveryClient
public class LoginFilter implements WebFilter {

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getPath().toString().equals("/login")) {
        	System.out.println("**** Is Login Request ****");
            return exchange.getRequest().getBody().next().flatMap(dataBuffer -> {
            	 String body=null;
				try {
					body = StreamUtils.copyToString(dataBuffer.asInputStream(), StandardCharsets.UTF_8);
				} catch (IOException e) {
					e.printStackTrace();
				}

                // Forward login request to User Service
                return WebClient.create()
                        .post()
                        .uri(userServiceUrl + "/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .flatMap(response -> {
                            if (Boolean.TRUE.equals(response.get("valid"))) {
                                // Generate JWT on successful validation
                                String token = JwtUtil.generateToken(response.get("username").toString(), new HashMap<>());
                                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                                        .bufferFactory().wrap(("{\"token\":\"" + token + "\"}").getBytes())));
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                                        .bufferFactory().wrap("{\"error\":\"Invalid username/password\"}".getBytes())));
                            }
                        });
            });
        }
//        else {
//        	String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        	System.out.println("**** Is Another Request ****");
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                String token = authHeader.substring(7);
//                System.out.println("**** Token Request **** : "+authHeader.strip());
//                if (JwtUtil.validateToken(token.strip())) {
//                	System.out.println("**** Is Valid Token Request **** : "+token);
//                    // Token is valid; proceed with the request
//                    return chain.filter(exchange);
//                }
//            }
//        }
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
        
        return chain.filter(exchange);
    }
}
