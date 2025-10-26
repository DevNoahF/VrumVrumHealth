package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final MotoristaRepository motoristaRepository;
    private final PacienteRepository pacienteRepository;
    private final AdmRepository admRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return admRepository.findByEmail(username)
                .map(adm -> new org.springframework.security.core.userdetails.User(
                        adm.getEmail(),
                        adm.getSenha(),
                        List.of(new SimpleGrantedAuthority("ADMIN"))
                ))
                .or(() -> motoristaRepository.findByEmail(username)
                        .map(motorista -> new org.springframework.security.core.userdetails.User(
                                motorista.getEmail(),
                                motorista.getSenha(),
                                List.of(new SimpleGrantedAuthority("MOTORISTA"))
                        )))
                .or(() -> pacienteRepository.findByEmail(username)
                        .map(paciente -> new org.springframework.security.core.userdetails.User(
                                paciente.getEmail(),
                                paciente.getSenha(),
                                List.of(new SimpleGrantedAuthority("PACIENTE"))
                        )))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario com o nome : " + username + " não encontrado"));
    }

//    public ResponseEntity<?> registerPaciente(@RequestBody RegisterDTO dto){
//        User usuario = new User(dto.getEmail(), passwordEncoder.encode(dto.getSenha()), RoleEnum.USER);
//        userR
//    }
}






//    // método para autenticar admin
//    public boolean autenticarAdm(String email, String senha){
//        return admRepository.findByEmail(email)
//                .map(adm -> passwordEncoder.matches(senha, adm.getSenhaHash()))
//                .orElse(false);
//    }

//    // método para autenticar paciente
//    public boolean autenticarPaciente(String email, String senha){
//        return pacienteRepository.findByEmail(email)
//                .map(paciente -> passwordEncoder.matches(senha, paciente.getSenhaHash()))
//                .orElse(false);
//    }

