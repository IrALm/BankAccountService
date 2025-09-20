package com.example.BanqueApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                  // Désactiver CSRF pour tests
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Tout autoriser
                .httpBasic(withDefaults());                   // Basic Auth avec la nouvelle API

        return http.build();
    }
}
