package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final Map<Long, AgendamentoDTO> agendamentos = new HashMap<>();
    private Long sequence = 1L;

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        return ResponseEntity.ok(new ArrayList<>(agendamentos.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentos.get(id);
        if (agendamento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criar(@RequestBody AgendamentoDTO dto) {
        dto.setId(sequence++);
        agendamentos.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizar(@PathVariable Long id, @RequestBody AgendamentoDTO dto) {
        AgendamentoDTO existente = agendamentos.get(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        // não altera o ID
        dto.setId(id);
        agendamentos.put(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!agendamentos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        agendamentos.remove(id);
        return ResponseEntity.noContent().build();
    }
}
