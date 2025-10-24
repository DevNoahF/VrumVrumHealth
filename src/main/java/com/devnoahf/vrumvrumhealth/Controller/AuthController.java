package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.UsersDTO;
import com.devnoahf.vrumvrumhealth.Entity.Paciente;
import com.devnoahf.vrumvrumhealth.Entity.Users;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PacienteRepository pacienteRepository;

    private final MotoristaRepository motoristaRepository;

    private final AdmRepository admRepository;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersDTO usersDTO){

    }


    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerPaciente(@RequestBody UsersDTO dto) {
        Users usuario = new Users(dto.getEmail(), passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(RoleEnum.PACIENTE);
        usuarioRepository.save(usuario);

        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);

        return ResponseEntity.ok("Conta Criada! Agora Complete seu Perfil!");
    }

}
