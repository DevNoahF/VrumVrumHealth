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

    // método para cadastrar paciente com senha criptografada
    public Paciente cadastrarPaciente(Paciente paciente) {
        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(paciente.getSenhaHash());
        paciente.setSenhaHash(senhaCriptografada);

        // Salva no banco
        return pacienteRepository.save(paciente);
    }


}
