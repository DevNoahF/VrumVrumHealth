package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Paciente cadastrarPaciente(Paciente paciente) {
        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(paciente.getSenha_hash());
        paciente.setSenha_hash(senhaCriptografada);


        // Salva no banco
        return pacienteRepository.save(paciente);
    }

}
