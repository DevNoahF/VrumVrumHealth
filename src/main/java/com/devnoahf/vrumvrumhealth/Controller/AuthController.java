package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Mapper.AdmMapper;
import com.devnoahf.vrumvrumhealth.Mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.Mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.Model.Adm;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.Security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final AdmRepository admRepository;
    private final MotoristaRepository motoristaRepository;
    private final PacienteRepository pacienteRepository;

    private final AdmMapper admMapper;
    private final PacienteMapper pacienteMapper;

    private final JwtTokenProvider jwtTokenProvider;

    // 🔹 LOGIN — funciona para todos os perfis
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String senha = loginData.get("senha");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );

            // Gera o token JWT
            String token = jwtTokenProvider.generateToken(authentication);
            UserDetails user = (UserDetails) authentication.getPrincipal();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login bem-sucedido!");
            response.put("email", user.getUsername());
            response.put("roles", user.getAuthorities());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas: " + e.getMessage());
        }
    }


    // REGISTER - ADM TEMPORARIO:
    @PostMapping("/register/adm-inicial")
    public ResponseEntity<AdmDTO> criarAdmInicial(@Valid @RequestBody AdmDTO admDTO) {
        if (admRepository.existsByEmail(admDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Adm adm = admMapper.toEntity(admDTO);
        admRepository.save(adm);

        return ResponseEntity.status(HttpStatus.CREATED).body(admMapper.toDTO(adm));
    }


    // 🔹 REGISTER — ADMIN
    @PostMapping("/register/adm")
    public ResponseEntity<?> registerAdm(@Valid @RequestBody AdmDTO admDTO) {
        if (admRepository.existsByEmail(admDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um administrador com este e-mail.");
        }

        Adm adm = admMapper.toEntity(admDTO);
        adm.setSenha(passwordEncoder.encode(adm.getSenha()));
        admRepository.save(adm);

        return ResponseEntity.status(HttpStatus.CREATED).body(admMapper.toDTO(adm));
    }

    // 🔹 REGISTER — MOTORISTA
    @PostMapping("/register/motorista")
    public ResponseEntity<?> registerMotorista(@Valid @RequestBody MotoristaDTO motoristaDTO) {
        if (motoristaRepository.existsByEmail(motoristaDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um motorista com este e-mail.");
        }

        Motorista motorista = MotoristaMapper.toEntity(motoristaDTO);
        motorista.setSenha(passwordEncoder.encode(motorista.getSenha()));
        motoristaRepository.save(motorista);

        return ResponseEntity.status(HttpStatus.CREATED).body(MotoristaMapper.toDTO(motorista));
    }

    // 🔹 REGISTER — PACIENTE
    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        if (pacienteRepository.existsByEmail(pacienteDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um paciente com este e-mail.");
        }

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));
        pacienteRepository.save(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toDTO(paciente));
    }
}
