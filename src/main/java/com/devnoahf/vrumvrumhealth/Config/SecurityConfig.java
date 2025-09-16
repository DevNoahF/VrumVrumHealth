package com.devnoahf.vrumvrumhealth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    public Boolean verifyPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder().matches(rawPassword, encodedPassword);
//    }

//    // Configuração de segurança HTTP para diferentes endpoints baseada nas roles
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configura as regras de segurança
//        http  // desabilita CSRF para simplificar (não recomendado para produção sem outras medidas)
//                .csrf(AbstractHttpConfigurer::disable)  // configura as autorizações baseadas em roles
//                .authorizeHttpRequests(auth -> auth // define as regras de autorização
//                        .requestMatchers("/admin/**").hasRole("ADMIN")  // apenas ADMIN pode acessar /admin/**
//                        .requestMatchers("/paciente/**").hasRole("PACIENTE") // apenas PACIENTE pode acessar /paciente/**
//                        .anyRequest().permitAll() // qualquer outra requisição é permitida sem autenticação
//                )
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // permite o formulário de login para todos
//                .logout(LogoutConfigurer::permitAll); // permite logout para todos
//        return http.build(); // constrói a cadeia de filtros de segurança
//    }

   
}
