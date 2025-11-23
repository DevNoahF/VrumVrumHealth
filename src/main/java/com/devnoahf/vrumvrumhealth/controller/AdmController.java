package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.AdmDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.model.Adm;
import com.devnoahf.vrumvrumhealth.service.AdmService;
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

@RestController
@RequestMapping("/adm")
@RequiredArgsConstructor
@Tag(name = "Administradores", description = "Gerenciamento de administradores")
public class AdmController {

    private final AdmService admService;

    // Criar admin normal (somente ADMIN)
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<AdmDTO> criar(@Valid @RequestBody AdmDTO admDTO) {
//        AdmDTO novoAdm = admService.cadastrarAdm(admDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novoAdm);
//    }

    // Criar admin inicial (qualquer um pode acessar)
//    @PostMapping("/criar-admin-inicial")
//    public ResponseEntity<AdmDTO> criarAdminInicial(@Valid @RequestBody AdmDTO admDTO) {
//        AdmDTO novoAdm = admService.cadastrarAdm(admDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novoAdm);
//    }


    // 🔹 Atualizar um administrador existente (somente o próprio admin ou outro admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar administrador", description = "Atualiza dados do administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador atualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> atualizar(@Valid @RequestBody AdmDTO admDTO, @PathVariable Long id, Authentication auth) {
        AdmDTO admExistente = admService.buscarPorId(id);
        if (admExistente == null) {
            throw new ResourceNotFoundException("Administrador com ID " + id + " não encontrado.");
        }

        // Se não for admin global, verifica se está atualizando o próprio perfil
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && !admExistente.getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizarAgendamentoPaciente seu próprio perfil.");
        }

        AdmDTO admAtualizado = admService.atualizarAdm(admDTO, id);
        return ResponseEntity.ok(admAtualizado);
    }

    // 🔹 Deletar um administrador (somente ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar administrador", description = "Remove um administrador pelo ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        AdmDTO adm = admService.buscarPorId(id);
        if (adm == null) {
            throw new ResourceNotFoundException("Administrador não encontrado.");
        }

        admService.deletarAdm(id);
        return ResponseEntity.ok("Administrador deletado com sucesso!");
    }

    // 🔹 Listar todos os administradores (somente ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar administradores", description = "Lista todos os administradores")
    public ResponseEntity<List<AdmDTO>> listar() {
        List<AdmDTO> admins = admService.listarAdmins();
        return ResponseEntity.ok(admins);
    }

    // 🔹 Buscar um administrador por ID (somente ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar administrador por ID", description = "Recupera um administrador pelo ID")
    public ResponseEntity<AdmDTO> buscarPorId(@PathVariable Long id) {
        AdmDTO adm = admService.buscarPorId(id);
        if (adm == null) {
            throw new ResourceNotFoundException("Administrador não encontrado.");
        }
        return ResponseEntity.ok(adm);
    }

    // 🔹 Retornar perfil do admin logado
    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Perfil do admin logado", description = "Retorna dados do admin autenticado")
    public ResponseEntity<Adm> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        Adm adm = admService.findByEmail(email);
        if (adm == null) {
            throw new ResourceNotFoundException("Administrador autenticado não encontrado.");
        }
        return ResponseEntity.ok(adm);
    }

    // 🔹 Alterar senha (somente o próprio admin)
    @PatchMapping("/mudarsenha")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mudar senha (admin)", description = "Altera a senha do administrador autenticado")
    public ResponseEntity<?> mudarSenha(
            @RequestParam String novaSenha,
            Authentication authentication) {

        String email = authentication.getName();
        admService.mudarSenha(email, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
