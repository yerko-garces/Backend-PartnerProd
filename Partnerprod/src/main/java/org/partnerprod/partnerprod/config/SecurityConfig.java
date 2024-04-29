package org.partnerprod.partnerprod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/**").permitAll() // Permitir peticiones
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> httpBasic.realmName("YourRealmName")); // Usar HTTP Basic con un realm configurado

        return http.build();
    }
}
