package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private AdmRepository admRepository;  // injeta o repositório de Adm

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o PasswordEncoder




    // método para autenticar admin
    public boolean autenticarAdm(String email, String senha){
        return admRepository.findByEmail(email)
                .map(adm -> passwordEncoder.matches(senha, adm.getSenhaHash()))
                .orElse(false);
    }

    // método para autenticar paciente
    public boolean autenticarPaciente(String email, String senha){
        return pacienteRepository.findByEmail(email)
                .map(paciente -> passwordEncoder.matches(senha, paciente.getSenhaHash()))
                .orElse(false);
    }
}
