package com.treizer.order_service.config.security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignSecurityConfig {

    @Bean
    public RequestInterceptor jwtInterceptor() {
        return template -> {
            
            Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof JwtAuthenticationToken jwt) {
                // String tokenValue = jwt.getToken().getTokenValue();

                template.header(
                    HttpHeaders.AUTHORIZATION,
                    // "Bearer "+ tokenValue
                    "Bearer "+ jwt.getToken().getTokenValue()
                );
            }
        };
    }

    @Bean
    public RequestInterceptor tracingInterceptor() {
        return template -> {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                
                Collections.list(request.getHeaderNames())
                    .stream()
                    .filter(headerName -> 
                        // List.of("traceparent", "b3", "x-b3-traceid", "x-b3-spanid").contains(headerName)
                        headerName.equalsIgnoreCase("traceparent") ||
                        headerName.equalsIgnoreCase("tracestate") ||
                        headerName.toLowerCase().startsWith("x-b3")
                    )
                    .forEach(headerName -> {
                        template.header(headerName, request.getHeader(headerName));
                    });
            }
        };
    }

}
