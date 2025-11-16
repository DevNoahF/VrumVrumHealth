package com.devnoahf.vrumvrumhealth.service;

import com.devnoahf.vrumvrumhealth.repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AdmRepository admRepository;
    private final MotoristaRepository motoristaRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username == null ? "" : username.trim();
        log.info("Tentando autenticar usuário com email: {}", email);

        // Verifica administrador
        Optional<UserDetails> adm = admRepository.findByEmail(email)
                .map(a -> User.withUsername(a.getEmail())
                        .password(a.getSenha())
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .build());
        if (adm.isPresent()) {
            log.info("Administrador encontrado: {}", email);
            return adm.get();
        }

        // Verifica motorista
        Optional<UserDetails> motorista = motoristaRepository.findByEmail(email)
                .map(m -> User.withUsername(m.getEmail())
                        .password(m.getSenha())
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_MOTORISTA")))
                        .build());
        if (motorista.isPresent()) {
            log.info("Motorista encontrado: {}", email);
            return motorista.get();
        }

        // Verifica paciente
        Optional<UserDetails> paciente = pacienteRepository.findByEmail(email)
                .map(p -> User.withUsername(p.getEmail())
                        .password(p.getSenha())
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_PACIENTE")))
                        .build());
        if (paciente.isPresent()) {
            log.info("Paciente encontrado: {}", email);
            return paciente.get();
        }

        // Se não achou em nenhum repositório
        log.error("Usuário não encontrado: {}", email);
        throw new UsernameNotFoundException("Usuário com e-mail " + email + " não encontrado.");
    }
}
