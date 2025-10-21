package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;


import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping("/teste")
    public String teste() {
        return "Agendamento controller funcionando ✅";
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AgendamentoDTO dto) {
        AgendamentoDTO novoAgendamento = agendamentoService.criarAgendamento(dto);
        if (novoAgendamento != null) {
            return ResponseEntity.ok(novoAgendamento);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Paciente não encontrado ou dados inválidos.");
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listar() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Agendamento não encontrado");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@RequestBody AgendamentoDTO dto, @PathVariable Long id) {
        AgendamentoDTO atualizado = agendamentoService.atualizarAgendamento(dto, id);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Agendamento não encontrado");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        if (agendamento != null) {
            agendamentoService.deletarAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Agendamento não encontrado");
        }
    }

    // Endpoint para atualizar somente o status do agendamento
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam StatusEnum status) {
        AgendamentoDTO atualizado = agendamentoService.atualizarStatus(id, status);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Agendamento não encontrado para atualização de status.");
        }
    }

}
