package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.Entity.Users;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class  AuthController {

    private final UsersRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final PacienteRepository pacienteRepository;

    private final MotoristaRepository motoristaRepository;

    private final AdmRepository admRepository;


    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerPaciente(@RequestBody RegisterDTO dto) {
        Users usuario = new Usuario(dto.getNome(), dto.getEmail(), passwordEncoder.encode(dto.getSenha()), Role.ROLE_PACIENTE);
        usuarioRepository.save(usuario);

        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);

        return ResponseEntity.ok("Paciente registrado com sucesso!");
    }

}
