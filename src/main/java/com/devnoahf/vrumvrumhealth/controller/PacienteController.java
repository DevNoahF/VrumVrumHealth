package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.PacienteDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    // 🔹 Cadastro de paciente (liberado publicamente)
    @PostMapping
    public ResponseEntity<PacienteDTO> criar(@Valid @RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteService.cadastrarPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteService.buscarPorIdPaciente(paciente.getId()));
    }

    // 🔹 Atualizar paciente (somente o próprio paciente ou admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE','ADMIN')")
    public ResponseEntity<?> atualizar(@Valid @RequestBody PacienteDTO pacienteDTO,
                                       @PathVariable Long id,
                                       Authentication auth) {
        PacienteDTO existente = pacienteService.buscarPorIdPaciente(id);
        if (existente == null) {
            throw new ResourceNotFoundException("Paciente com ID " + id + " não encontrado.");
        }

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !existente.getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizar seu próprio perfil.");
        }

        PacienteDTO atualizado = pacienteService.atualizarPaciente(pacienteDTO, id);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar paciente (somente admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        PacienteDTO pacienteDTO = pacienteService.buscarPorIdPaciente(id);
        if (pacienteDTO == null) {
            throw new ResourceNotFoundException("Paciente não encontrado.");
        }

        pacienteService.deletarPaciente(id);
        return ResponseEntity.ok("Paciente deletado com sucesso!");
    }

    // 🔹 Listar todos (somente admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PacienteDTO>> listar() {
        List<PacienteDTO> pacientes = pacienteService.listarPaciente();
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 Buscar paciente por ID (somente admin)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        PacienteDTO pacienteDTO = pacienteService.buscarPorIdPaciente(id);
        if (pacienteDTO == null) {
            throw new ResourceNotFoundException("Paciente não encontrado.");
        }
        return ResponseEntity.ok(pacienteDTO);
    }

    // 🔹 Retornar perfil do paciente logado
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<PacienteDTO> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        Paciente paciente = pacienteService.findByEmail(email);

        if (paciente == null) {
            throw new ResourceNotFoundException("Paciente autenticado não encontrado.");
        }

        PacienteDTO pacienteDTO = pacienteMapper.toDTO(paciente);
        return ResponseEntity.ok(pacienteDTO);
    }


    // 🔹 Mudar senha (somente o próprio paciente)
    @PatchMapping("/mudarsenha")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<?> mudarSenha(@RequestParam String novaSenha,
                                        Authentication authentication) {
        String email = authentication.getName();
        pacienteService.mudarSenhaPaciente(email, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
