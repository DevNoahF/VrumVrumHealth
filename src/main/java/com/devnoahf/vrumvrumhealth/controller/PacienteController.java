package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.PacienteDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Gerenciamento de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;



    // 🔹 Atualizar paciente (somente o próprio paciente ou admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE','ADMIN')")
    @Operation(summary = "Atualizar paciente", description = "Atualiza dados do paciente. Apenas o próprio paciente ou admin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente atualizado"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
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
            throw new BadRequestException("Você só pode atualizarAgendamentoPaciente seu próprio perfil.");
        }

        PacienteDTO atualizado = pacienteService.atualizarPaciente(pacienteDTO, id);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar paciente (somente admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar paciente", description = "Remove um paciente pelo ID (ADMIN)")
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
    @Operation(summary = "Listar pacientes", description = "Lista todos os pacientes (ADMIN)")
    public ResponseEntity<List<PacienteDTO>> listar() {
        List<PacienteDTO> pacientes = pacienteService.listarPaciente();
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 Buscar paciente por ID (somente admin)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar paciente por ID", description = "Recupera um paciente pelo ID (ADMIN)")
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
    @Operation(summary = "Meu perfil (paciente)", description = "Retorna dados do paciente autenticado")
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
    @Operation(summary = "Mudar senha (paciente)", description = "Altera a senha do paciente autenticado")
    public ResponseEntity<?> mudarSenha(@RequestParam String novaSenha,
                                        Authentication authentication) {
        String email = authentication.getName();
        pacienteService.mudarSenhaPaciente(email, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }


}
