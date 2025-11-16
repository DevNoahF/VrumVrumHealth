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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    // 🔹 Listar todos — ADMIN e MOTORISTA
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<List<VeiculoDTO>> listarTodos() {
        List<VeiculoDTO> lista = veiculoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // 🔹 Buscar por ID — ADMIN e MOTORISTA
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<VeiculoDTO> buscarPorId(@PathVariable Long id) {
        VeiculoDTO dto = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // 🔹 Criar — apenas ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        try {
            VeiculoDTO atualizado = veiculoService.atualizar(id, veiculoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    // 🔹 Deletar — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
