package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.VeiculoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final Map<Long, VeiculoDTO> veiculos = new HashMap<>();
    private Long sequence = 1L;

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarTodos() {
        return ResponseEntity.ok(new ArrayList<>(veiculos.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> buscarPorId(@PathVariable Long id) {
        VeiculoDTO veiculo = veiculos.get(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> criar(@RequestBody VeiculoDTO dto) {
        dto.setId(sequence++);
        veiculos.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizar(@PathVariable Long id, @RequestBody VeiculoDTO dto) {
        VeiculoDTO existente = veiculos.get(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        veiculos.put(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!veiculos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculos.remove(id);
        return ResponseEntity.noContent().build();
    }
}
