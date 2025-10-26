package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Service.DiarioBordoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diarioBordo")
@RequiredArgsConstructor
public class DiarioBordoController {

    private final DiarioBordoService service;

    // 🔹 Criar novo diário — apenas ADMIN ou MOTORISTA
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<DiarioBordoDTO> criar(@RequestBody DiarioBordoDTO dto, Authentication auth) {
        DiarioBordoDTO salvo = service.salvar(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 🔹 Atualizar diário — apenas ADMIN ou o próprio motorista dono
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<DiarioBordoDTO> update(@PathVariable Long id, @RequestBody DiarioBordoDTO dto, Authentication auth) {
        DiarioBordoDTO atualizado = service.update(id, dto, auth);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar diário — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Listar todos — apenas ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DiarioBordoDTO>> listAll() {
        List<DiarioBordoDTO> diarios = service.listAll();
        return ResponseEntity.ok(diarios);
    }

    // 🔹 Buscar por ID — ADMIN pode ver todos, motorista só os próprios
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<DiarioBordoDTO> listById(@PathVariable Long id, Authentication auth) {
        DiarioBordoDTO diario = service.listById(id, auth);
        return ResponseEntity.ok(diario);
    }

    // 🔹 Motorista: listar apenas os seus próprios diários
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTORISTA')")
    public ResponseEntity<List<DiarioBordoDTO>> listarMeusDiarios(Authentication auth) {
        List<DiarioBordoDTO> diarios = service.listarPorMotorista(auth.getName());
        return ResponseEntity.ok(diarios);
    }
}
