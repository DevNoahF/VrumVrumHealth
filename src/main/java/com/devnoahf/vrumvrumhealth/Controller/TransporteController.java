package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.TransporteDTO;
import com.devnoahf.vrumvrumhealth.Service.TransporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transporte")
@RequiredArgsConstructor
public class TransporteController {

    private final TransporteService transporteService;

    // 🔹 Criar transporte — apenas ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransporteDTO> criar(@RequestBody TransporteDTO dto) {
        TransporteDTO novo = transporteService.criarTransporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // 🔹 Atualizar transporte — apenas ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransporteDTO> atualizar(@PathVariable Long id, @RequestBody TransporteDTO dto) {
        TransporteDTO atualizado = transporteService.atualizarTransporte(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 Deletar transporte — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        transporteService.deletarTransporte(id);
        return ResponseEntity.ok("Transporte deletado com sucesso!");
    }

    // 🔹 Listar todos os transportes — MOTORISTA e ADMIN
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA')")
    public ResponseEntity<List<TransporteDTO>> listarTodos() {
        List<TransporteDTO> lista = transporteService.listarTransportes();
        return ResponseEntity.ok(lista);
    }

    // 🔹 Buscar por ID — ADMIN e MOTORISTA podem ver qualquer um, paciente apenas o seu
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOTORISTA', 'PACIENTE')")
    public ResponseEntity<TransporteDTO> buscarPorId(@PathVariable Long id) {
        TransporteDTO transporte = transporteService.buscarPorIdAutenticado(id);
        return ResponseEntity.ok(transporte);
    }

    // 🔹 Paciente: ver apenas o transporte vinculado ao seu agendamento
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<TransporteDTO> buscarMeuTransporte(Authentication auth) {
        TransporteDTO transporte = transporteService.buscarTransportePorPaciente(auth.getName());
        return ResponseEntity.ok(transporte);
    }
}
