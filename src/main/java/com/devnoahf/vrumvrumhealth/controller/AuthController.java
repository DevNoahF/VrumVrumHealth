package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.AdmDTO;
import com.devnoahf.vrumvrumhealth.dto.LoginDTO;
import com.devnoahf.vrumvrumhealth.dto.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.dto.PacienteDTO;
import com.devnoahf.vrumvrumhealth.mapper.AdmMapper;
import com.devnoahf.vrumvrumhealth.mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.model.Adm;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.repository.AdmRepository;
import com.devnoahf.vrumvrumhealth.repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AdmRepository admRepository;
    private final MotoristaRepository motoristaRepository;
    private final PacienteRepository pacienteRepository;

    private final AdmMapper admMapper;
    private final PacienteMapper pacienteMapper;

    private final JwtTokenProvider jwtTokenProvider;

    // 🔹 LOGIN — funciona para todos os perfis
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginData) {
        try {
            String email = loginData.getEmail();
            String senha = loginData.getSenha();

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

            // Set Authorization header and cookie (so Swagger / browsers can send automatically)
            // Cookie value must not contain spaces per RFC; store raw token (header still uses 'Bearer ')
            ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                    .path("/")
                    .httpOnly(false)
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas: " + e.getMessage());
        }
    }


    // REGISTER - ADM TEMPORARIO:
    @PostMapping("/register/adm-inicial")
    public ResponseEntity<?> criarAdmInicial(@Valid @RequestBody AdmDTO admDTO) {
        if (admRepository.existsByEmail(admDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Adm adm = admMapper.toEntity(admDTO);
        admRepository.save(adm);

        // Generate token for the newly created admin
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(adm.getEmail(), null, authorities);
        String token = jwtTokenProvider.generateToken(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Administrador criado com sucesso");
        response.put("user", admMapper.toDTO(adm));
        response.put("token", token);

        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .path("/")
                .httpOnly(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }


    // 🔹 REGISTER — ADMIN
    @PostMapping("/register/adm")
    public ResponseEntity<?> registerAdm(@Valid @RequestBody AdmDTO admDTO) {
        if (admRepository.existsByEmail(admDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um administrador com este e-mail.");
        }

        Adm adm = admMapper.toEntity(admDTO);
        admRepository.save(adm);

        // Generate token for new admin
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(adm.getEmail(), null, authorities);
        String token = jwtTokenProvider.generateToken(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Administrador criado com sucesso");
        response.put("user", admMapper.toDTO(adm));
        response.put("token", token);

        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .path("/")
                .httpOnly(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    // 🔹 REGISTER — MOTORISTA
    @PostMapping("/register/motorista")
    public ResponseEntity<?> registerMotorista(@Valid @RequestBody MotoristaDTO motoristaDTO) {
        if (motoristaRepository.existsByEmail(motoristaDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um motorista com este e-mail.");
        }

        Motorista motorista = MotoristaMapper.toEntity(motoristaDTO);
        motoristaRepository.save(motorista);

        // Generate token for new motorista
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_MOTORISTA"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(motorista.getEmail(), null, authorities);
        String token = jwtTokenProvider.generateToken(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Motorista criado com sucesso");
        response.put("user", MotoristaMapper.toDTO(motorista));
        response.put("token", token);

        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .path("/")
                .httpOnly(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    // 🔹 REGISTER — PACIENTE
    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        if (pacienteRepository.existsByEmail(pacienteDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um paciente com este e-mail.");
        }

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        pacienteRepository.save(paciente);

        // Generate token for new paciente
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_PACIENTE"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(paciente.getEmail(), null, authorities);
        String token = jwtTokenProvider.generateToken(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Paciente criado com sucesso");
        response.put("user", pacienteMapper.toDTO(paciente));
        response.put("token", token);

        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .path("/")
                .httpOnly(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}
