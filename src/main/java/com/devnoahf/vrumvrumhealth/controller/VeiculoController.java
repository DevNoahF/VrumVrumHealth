package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.VeiculoDTO;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    // 🔹 Listar todos — ADMIN e MOTORISTA
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Listar veículos", description = "Lista todos os veículos (ADMIN e MOTORISTA)")
    public ResponseEntity<List<VeiculoDTO>> listarTodos() {
        List<VeiculoDTO> lista = veiculoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // 🔹 Buscar por ID — ADMIN e MOTORISTA
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    @Operation(summary = "Buscar veículo por ID", description = "Recupera um veículo pelo ID (ADMIN e MOTORISTA)")
    public ResponseEntity<VeiculoDTO> buscarPorId(@PathVariable Long id) {
        VeiculoDTO dto = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // 🔹 Criar — apenas ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar veículo", description = "Cria um novo veículo (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> criar(@RequestBody VeiculoDTO veiculoDTO) {
        try {
            VeiculoDTO novo = veiculoService.salvar(veiculoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar veículo: " + e.getMessage());
        }
    }

    // 🔹 Atualizar — apenas ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar veículo", description = "Atualiza um veículo existente (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo atualizado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        try {
            VeiculoDTO atualizado = veiculoService.atualizar(id, veiculoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizarAgendamentoPaciente veículo: " + e.getMessage());
        }
    }

    // 🔹 Deletar — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar veículo", description = "Remove um veículo pelo ID (ADMIN)")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            veiculoService.deletar(id);
            return ResponseEntity.ok("Veículo deletado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar veículo: " + e.getMessage());
        }
    }
}
