package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Service.MotoristaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorista")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService service;

    // 🔹 Criar novo motorista (acessível publicamente via /auth/register/motorista)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MotoristaDTO> save(@Valid @RequestBody MotoristaDTO dto) {
        Motorista motorista = MotoristaMapper.toEntity(dto);
        Motorista motoristaSalvo = service.save(motorista);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MotoristaMapper.toDTO(motoristaSalvo));
    }

    // 🔹 Atualizar motorista existente (somente Admin ou o próprio motorista)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOTORISTA')")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MotoristaDTO dto, Authentication auth) {
        Motorista motoristaExistente = service.listById(id);

        if (motoristaExistente == null) {
            throw new ResourceNotFoundException("Motorista não encontrado com ID: " + id);
        }

        // Se não for admin, verifica se o email do logado é o mesmo do motorista
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && !motoristaExistente.getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizar seu próprio perfil.");
        }

        Motorista motoristaAtualizado = service.update(id, MotoristaMapper.toEntity(dto));
        return ResponseEntity.ok(MotoristaMapper.toDTO(motoristaAtualizado));
    }

    // 🔹 Deletar motorista (somente Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<?> mudarSenha(
            @RequestParam String novaSenha,
            Authentication authentication) {

        String email = authentication.getName();
        service.mudarSenha(email, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
