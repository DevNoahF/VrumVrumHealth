package com.devnoahf.vrumvrumhealth.Config;

import com.devnoahf.vrumvrumhealth.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpoints públicos (cadastro e login)
                        .requestMatchers("/auth/**").permitAll()

                        // 🔐 Regras de acesso
                        .requestMatchers("/adm/**").hasRole("ADMIN")
                        // Motorista pode ver apenas seu perfil (/motorista/me)
                        .requestMatchers("/motorista/me", "/motorista/mudar-senha").hasRole("MOTORISTA")
                        // Paciente pode ver apenas seu perfil (/paciente/me)
                        .requestMatchers("/paciente/me", "/paciente/mudar-senha").hasRole("PACIENTE")

                        // Admin pode listar todos os motoristas e pacientes
                        .requestMatchers("/motorista/**", "/paciente/**").hasRole("ADMIN")

                        // 🔒 Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )
                .userDetailsService(authService)
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
