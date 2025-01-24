package com.propertywale.GatewayService.configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomGlobalFilter {

//    @Bean
//    public GlobalFilter globalFilter() {
//        return (exchange, chain) -> {
//            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//            if (authHeader != null) {
//                exchange.getRequest()
//                        .mutate()
//                        .header("Authorization", authHeader)
//                        .build();
//            }
//            return chain.filter(exchange);
//        };
//    }
}

