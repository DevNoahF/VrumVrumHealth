package com.devnoahf.vrumvrumhealth.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //avisa o spring que essa classe é de configuração
@EnableWebSecurity //habilita a segurança web do spring que esta configuradadentro dessa classe
@RequiredArgsConstructor
public class SecurityConfig {


//    private final ConfigurableWebServerFactory configurableWebServerFactory;
//
//
//    public SecurityConfig(ConfigurableWebServerFactory configurableWebServerFactory) {
//        this.configurableWebServerFactory = configurableWebServerFactory;
//    }


    // Configuração de segurança HTTP para permitir todas as requisições sem autenticação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desativa a proteção CSRF (Cross-Site Request Forgery)
                // Essencial para permitir requisições POST/PUT/DELETE de clientes como Postman ou um frontend separado.
                .csrf(csrf -> csrf.disable())

                // 2. Autoriza todas as requisições
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite acesso a QUALQUER requisição sem autenticação
                );

        return http.build();
    }


    // Configuração de segurança HTTP para proteger endpoints específicos
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(HttpMethod.POST, "/auth/register/").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//                        .anyRequest().authenticated()
//                );
//        //.addFilter()
//
//        return http.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



//    public Boolean verifyPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder().matches(rawPassword, encodedPassword);
//    }

//
   

