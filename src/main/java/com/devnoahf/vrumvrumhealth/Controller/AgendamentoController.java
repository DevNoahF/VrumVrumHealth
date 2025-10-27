package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;


import java.util.List;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    // 🔹 Teste simples
    @GetMapping("/teste")
    public String teste() {
        return "✅ Agendamento controller funcionando!";
    }

    // 🔹 Criar agendamento — Paciente e Admin podem
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AgendamentoDTO>> listar() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    // 🔹 Buscar por ID — ADMIN e PACIENTE (somente o próprio)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, Authentication auth) {
        try {
            AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);

            // 🔒 Se for paciente, valida se é o dono do agendamento
            if (auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {

                String emailLogado = auth.getName();
                String emailPaciente = agendamentoService.buscarEmailPorIdPaciente(agendamento.getPacienteId());

                if (!emailLogado.equals(emailPaciente)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Você não tem permissão para acessar este agendamento.");
                }
            }

            return ResponseEntity.ok(agendamento);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar agendamento: " + e.getMessage());
        }
    }


    // 🔹 Endpoint para o paciente listar apenas os próprios agendamentos
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<?> listarMeusAgendamentos(Authentication auth) {
        String email = auth.getName();
        List<AgendamentoDTO> meusAgendamentos = agendamentoService.listarAgendamentosPorPaciente(email);

        if (meusAgendamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum agendamento encontrado para o paciente logado.");
        }

        return ResponseEntity.ok(meusAgendamentos);
    }

    // 🔹 Atualizar agendamento — ADMIN e PACIENTE (somente o próprio)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody AgendamentoDTO dto,
            Authentication auth) {
        try {
            AgendamentoDTO atualizado = agendamentoService.atualizarAgendamento(id, dto);

            // 🔒 Se for paciente, valida se está atualizando o próprio agendamento
            if (auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {

                String emailLogado = auth.getName();
                String emailPaciente = agendamentoService.buscarEmailPorIdPaciente(atualizado.getPacienteId());

                if (!emailLogado.equals(emailPaciente)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Você não tem permissão para atualizar este agendamento.");
                }
            }

            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }


    // 🔹 Deletar agendamento — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        if (agendamento != null) {
            agendamentoService.deletarAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar agendamento: " + e.getMessage());
        }
    }

}
