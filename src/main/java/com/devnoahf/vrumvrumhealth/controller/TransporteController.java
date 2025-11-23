package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.TransporteDTO;
import com.devnoahf.vrumvrumhealth.service.TransporteService;
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
@RequestMapping("/transporte")
@RequiredArgsConstructor
@Tag(name = "Transportes", description = "Gerenciamento de transportes e associações a agendamentos")
public class TransporteController {

    private final TransporteService transporteService;

    // 🔹 Criar transporte — apenas ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar transporte", description = "Cria um novo transporte (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transporte criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<TransporteDTO> criar(@RequestBody TransporteDTO dto) {
        TransporteDTO novo = transporteService.criarTransporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // 🔹 Atualizar transporte — apenas ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar transporte", description = "Atualiza dados do transporte (ADMIN)")
    public ResponseEntity<TransporteDTO> atualizar(@PathVariable Long id, @RequestBody TransporteDTO dto) {
        TransporteDTO atualizado = transporteService.atualizarTransporte(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar transporte — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar transporte", description = "Remove um transporte pelo ID (ADMIN)")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        transporteService.deletarTransporte(id);
        return ResponseEntity.ok("Transporte deletado com sucesso!");
    }

    // 🔹 Listar todos os transportes — MOTORISTA e ADMIN
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Listar transportes", description = "Lista todos os transportes (ADMIN e MOTORISTA)")
    public ResponseEntity<List<TransporteDTO>> listarTodos() {
        List<TransporteDTO> lista = transporteService.listarTransportes();
        return ResponseEntity.ok(lista);
    }

    // 🔹 Buscar por ID — ADMIN e MOTORISTA podem ver qualquer um, paciente apenas o seu
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA', 'PACIENTE')")
    @Operation(summary = "Buscar transporte por ID", description = "Recupera um transporte pelo ID")
    public ResponseEntity<TransporteDTO> buscarPorId(@PathVariable Long id, Authentication auth) {
        TransporteDTO transporte = transporteService.buscarPorIdAutenticado(id, auth);
        return ResponseEntity.ok(transporte);
    }


    // 🔹 Paciente: ver apenas o transporte vinculado ao seu agendamento
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Meu transporte (paciente)", description = "Retorna o transporte vinculado ao paciente autenticado")
    public ResponseEntity<TransporteDTO> buscarMeuTransporte(Authentication auth) {
        TransporteDTO transporte = transporteService.buscarTransportePorPaciente(auth.getName());
        return ResponseEntity.ok(transporte);
    }
}
