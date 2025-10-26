package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
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


    private final PasswordEncoder passwordEncoder;

    private final PacienteRepository pacienteRepository;

    private final MotoristaRepository motoristaRepository;

    private final AdmRepository admRepository;


    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerUser(@RequestBody PacienteDTO pacienteDTO) {
        if (PacienteRepository.existsByEmail(pacienteDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Erro: Email já está em uso!");
        }
        // Cria novo usuário
        Paciente paciente = PacienteMapper.toEntity(pacienteDTO);
        paciente.setRoleEnum(RoleEnum.valueOf("PACIENTE"));
        pacienteRepository.save(paciente);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

}
