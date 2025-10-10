package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Service.AgendamentoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    // Teste simples
    @GetMapping("/teste")
    public String teste() {
        return "agendamento controller funcionando ✅";
    }

    //  Criar
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AgendamentoDTO dto) {
        try {
            AgendamentoDTO novo = agendamentoService.criarAgendamento(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar agendamento: " + e.getMessage());
        }
    }

    //  Listar todos
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listar() {
        List<AgendamentoDTO> lista = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(lista);
    }

    //  Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
            return ResponseEntity.ok(agendamento);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar agendamento: " + e.getMessage());
        }
    }

    //  Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AgendamentoDTO dto) {
        try {
            AgendamentoDTO atualizado = agendamentoService.atualizarAgendamento(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    //  Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            agendamentoService.deletarAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar agendamento: " + e.getMessage());
        }
    }
}
