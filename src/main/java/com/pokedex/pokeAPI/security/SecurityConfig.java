package com.pokedex.pokeAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers(HttpMethod.GET, "/v1/login", "/v3/**", "/swagger-ui/**","/swagger-ui.html", "/swagger-ui.html/**",
                    "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer().jwt();
        return http.build();
    }
}
