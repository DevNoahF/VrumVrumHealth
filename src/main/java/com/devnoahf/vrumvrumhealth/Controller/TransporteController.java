package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.TransporteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transporte")
public class TransporteController {

    private final Map<Long, TransporteDTO> transportes = new HashMap<>();
    private Long sequence = 1L;

    @GetMapping
    public ResponseEntity<List<TransporteDTO>> listarTodos() {
        return ResponseEntity.ok(new ArrayList<>(transportes.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransporteDTO> buscarPorId(@PathVariable Long id) {
        TransporteDTO transporte = transportes.get(id);
        if (transporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transporte);
    }

    @PostMapping
    public ResponseEntity<TransporteDTO> criar(@RequestBody TransporteDTO dto) {
        dto.setId(sequence++);
        dto.setDataCriacao(dto.getDataCriacao() == null ? java.time.LocalDate.now() : dto.getDataCriacao());
        dto.setDataAtualizacao(java.time.LocalDate.now());
        transportes.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransporteDTO> atualizar(@PathVariable Long id, @RequestBody TransporteDTO dto) {
        TransporteDTO existente = transportes.get(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        dto.setDataCriacao(existente.getDataCriacao());
        dto.setDataAtualizacao(java.time.LocalDate.now());
        transportes.put(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!transportes.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        transportes.remove(id);
        return ResponseEntity.noContent().build();
    }
}
