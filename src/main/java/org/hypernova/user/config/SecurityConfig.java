package org.hypernova.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Users/eunii/Documents/CODE/HyperNova/src/main/resources/static/index.html", "/static/**", "/ws/**", "/api/user/signup", "/api/user/login", "/signal/**", "/call/**").permitAll()
                        .anyRequest().authenticated() // 나머지 요청 인증 필요
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // frameOptions 비활성화
                .httpBasic(httpBasic -> httpBasic.realmName("HyperNova App")); // 기본 인증
        return http.build();
    }
}