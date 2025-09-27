package com.devnoahf.vrumvrumhealth.Config;

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

    // Configuração de segurança HTTP para diferentes endpoints baseada nas roles
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable()) // desabilita CSRF para simplificar (não recomendado para produção sem outras medidas)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, "/adm").hasRole("ADMIN") // apenas ADMIN pode acessar /adm
//                        .anyRequest().authenticated()
//                )
//                .build();
//    }

    // security fiter chain é uma cadeia de filtros que processam as requisições HTTP para aplicar regras de segurança
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // mais utilizada em apis resfful -> autenticação stateless, cada requisição é independente e deve conter todas as informações necessárias para autenticação
//autenticação stateful, o estado da sessão do usuário é mantido no servidor entre as requisições

}
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

   

