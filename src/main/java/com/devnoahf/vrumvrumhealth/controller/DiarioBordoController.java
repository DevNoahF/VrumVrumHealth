package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.service.DiarioBordoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/diarioBordo")
@RequiredArgsConstructor
@Tag(name = "Diário de Bordo", description = "Registros de viagens e quilometragens")
public class DiarioBordoController {

    private final DiarioBordoService service;

    // 🔹 Criar novo diário — apenas ADMIN ou MOTORISTA
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Criar diário de bordo", description = "Cria um novo registro de diário de bordo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Diário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<DiarioBordoDTO> criar(@RequestBody DiarioBordoDTO dto, Authentication auth) {
        DiarioBordoDTO salvo = service.salvar(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 🔹 Atualizar diário — apenas ADMIN ou o próprio motorista dono
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Atualizar diário de bordo", description = "Atualiza um registro de diário de bordo")
    public ResponseEntity<DiarioBordoDTO> update(@PathVariable Long id, @RequestBody DiarioBordoDTO dto, Authentication auth) {
        DiarioBordoDTO atualizado = service.update(id, dto, auth);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Atualizar apenas a quilometragem final — endpoint específico
    @PutMapping("/{id}/quilometragemFinal")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Adicionar quilometragem final", description = "Atualiza apenas a quilometragem final do diário")
    public ResponseEntity<DiarioBordoDTO> adicionarQuilometragemFinal(@PathVariable Long id, @RequestBody DiarioBordoDTO dto, Authentication auth) {
        DiarioBordoDTO atualizado = service.adicionarQuilometragemFinal(id, dto, auth);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar diário — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar diário de bordo", description = "Remove um registro de diário de bordo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Listar todos — apenas ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar diários de bordo", description = "Lista todos os registros de diário de bordo (ADMIN)")
    public ResponseEntity<List<DiarioBordoDTO>> listAll() {
        List<DiarioBordoDTO> diarios = service.listAll();
        return ResponseEntity.ok(diarios);
    }

    // 🔹 Buscar por ID — ADMIN pode ver todos, motorista só os próprios
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Buscar diário por ID", description = "Recupera um registro de diário pelo ID")
    public ResponseEntity<DiarioBordoDTO> listById(@PathVariable Long id, Authentication auth) {
        DiarioBordoDTO diario = service.listById(id, auth);
        return ResponseEntity.ok(diario);
    }

    // 🔹 Motorista: listar apenas os seus próprios diários
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTORISTA')")
    @Operation(summary = "Meus diários de bordo", description = "Lista apenas os diários do motorista autenticado")
    public ResponseEntity<List<DiarioBordo>> listarMeusDiarios(Authentication auth) {
        List<DiarioBordo> diarios = service.listarPorMotorista(auth.getName());
        return ResponseEntity.ok(diarios);
    }
}
