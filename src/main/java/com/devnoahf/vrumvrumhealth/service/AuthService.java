package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentando autenticar usuário com email: {}", username);

        // Verifica administrador
        Optional<UserDetails> adm = admRepository.findByEmail(username)
                .map(a -> new User(
                        a.getEmail(),
                        a.getSenha(),
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ));
        if (adm.isPresent()) {
            log.info("Administrador autenticado: {}", username);
            return adm.get();
        }

        // Verifica motorista
        Optional<UserDetails> motorista = motoristaRepository.findByEmail(username)
                .map(m -> new User(
                        m.getEmail(),
                        m.getSenha(),
                        List.of(new SimpleGrantedAuthority("ROLE_MOTORISTA"))
                ));
        if (motorista.isPresent()) {
            log.info("Motorista autenticado: {}", username);
            return motorista.get();
        }

        // Verifica paciente
        Optional<UserDetails> paciente = pacienteRepository.findByEmail(username)
                .map(p -> new User(
                        p.getEmail(),
                        p.getSenha(),
                        List.of(new SimpleGrantedAuthority("ROLE_PACIENTE"))
                ));
        if (paciente.isPresent()) {
            log.info("Paciente autenticado: {}", username);
            return paciente.get();
        }

        // Se não achou em nenhum repositório
        log.error("Usuário não encontrado: {}", username);
        throw new UsernameNotFoundException("Usuário com e-mail " + username + " não encontrado.");
    }
}
