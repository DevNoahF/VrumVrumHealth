package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.service.MotoristaService;
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
@RequestMapping("/motorista")
@RequiredArgsConstructor
@Tag(name = "Motoristas", description = "Gerenciamento de motoristas")
public class MotoristaController {

    private final MotoristaService service;

    @GetMapping("/teste")
    public String teste() {
        return "ok";
    }


    // 🔹 Criar novo motorista (acessível publicamente via /auth/register/motorista)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar motorista", description = "Cria um novo motorista")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Motorista criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<MotoristaDTO> save(@Valid @RequestBody MotoristaDTO dto) {
        Motorista motorista = MotoristaMapper.toEntity(dto);
        Motorista motoristaSalvo = service.save(motorista);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MotoristaMapper.toDTO(motoristaSalvo));
    }

    // 🔹 Atualizar motorista existente (somente Admin ou o próprio motorista)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOTORISTA')")
    @Operation(summary = "Atualizar motorista", description = "Atualiza dados do motorista. Apenas admin ou o próprio motorista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motorista atualizado"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MotoristaDTO dto, Authentication auth) {
        Motorista motoristaExistente = service.listById(id);

        if (motoristaExistente == null) {
            throw new ResourceNotFoundException("Motorista não encontrado com ID: " + id);
        }

        // Se não for admin, verifica se o email do logado é o mesmo do motorista
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && !motoristaExistente.getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizarAgendamentoPaciente seu próprio perfil.");
        }

        Motorista motoristaAtualizado = MotoristaMapper.toEntityUpdate(motoristaExistente, dto);
        Motorista salvo = service.update(id, motoristaAtualizado);

        return ResponseEntity.ok(MotoristaMapper.toDTO(salvo));
    }

    // 🔹 Deletar motorista (somente Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar motorista", description = "Remove um motorista pelo ID")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Motorista motorista = service.listById(id);
        if (motorista == null) {
            throw new ResourceNotFoundException("Motorista não encontrado com ID: " + id);
        }

        service.delete(id);
        return ResponseEntity.ok("Motorista deletado com sucesso!");
    }

    // 🔹 Buscar todos os motoristas (somente Admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar motoristas", description = "Lista todos os motoristas (ADMIN)")
    public ResponseEntity<List<MotoristaDTO>> listAll() {
        List<MotoristaDTO> motoristas = service.listAll()
                .stream()
                .map(MotoristaMapper::toDTO)
                .toList();

        return ResponseEntity.ok(motoristas);
    }

    // 🔹 Buscar motorista por ID (somente Admin)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar motorista por ID", description = "Recupera um motorista pelo ID (ADMIN)")
    public ResponseEntity<MotoristaDTO> listById(@PathVariable Long id) {
        Motorista motorista = service.listById(id);
        if (motorista == null) {
            throw new ResourceNotFoundException("Motorista não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(MotoristaMapper.toDTO(motorista));
    }

    // 🔹 Retornar o perfil do motorista autenticado
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTORISTA')")
    @Operation(summary = "Meu perfil (motorista)", description = "Retorna dados do motorista autenticado")
    public ResponseEntity<MotoristaDTO> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        Motorista motorista = service.findByEmail(email);
        if (motorista == null) {
            throw new ResourceNotFoundException("Motorista autenticado não encontrado.");
        }
        return ResponseEntity.ok(MotoristaMapper.toDTO(motorista));
    }

    // 🔹 Alterar senha (somente o próprio motorista)
    @PatchMapping("/mudar-senha")
    @PreAuthorize("hasRole('MOTORISTA')")
    @Operation(summary = "Mudar senha (motorista)", description = "Altera a senha do motorista autenticado")
    public ResponseEntity<?> mudarSenha(
            @RequestParam String novaSenha,
            Authentication authentication) {

        String email = authentication.getName();
        service.mudarSenha(email, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
