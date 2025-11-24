package com.devnoahf.vrumvrumhealth.controller;

import com.devnoahf.vrumvrumhealth.dto.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.enums.StatusComprovanteEnum;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "CRUD e consultas de agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;



    //  Criar agendamento — Paciente e Admin podem
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento para um paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> criar(@Valid @RequestBody AgendamentoDTO dto) {
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
    @Operation(summary = "Listar agendamentos", description = "Lista todos os agendamentos (ADMIN)")
    public ResponseEntity<List<AgendamentoDTO>> listar() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    // 🔹 Buscar por ID — ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Buscar por ID", description = "Recupera um agendamento pelo ID (ADMIN)")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, Authentication auth) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }


    //Endpoint para o paciente listar apenas os próprios agendamentos
    @GetMapping("/me")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Listar meus agendamentos", description = "Lista agendamentos do paciente logado")
    public ResponseEntity<?> listarMeusAgendamentos(Authentication auth) {
        String email = auth.getName();
        List<AgendamentoDTO> meusAgendamentos = agendamentoService.listarAgendamentosPorPaciente(email);

        if (meusAgendamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum agendamento encontrado para o paciente logado.");
        }

        return ResponseEntity.ok(meusAgendamentos);
    }

    // Atualizar agendamento — para pacientes
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE')")
    @Operation(summary = "Atualizar agendamento (paciente)", description = "Atualiza parcialmente um agendamento. Campos não enviados permanecem inalterados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> atualizarAgendamentoPaciente(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoDTO dto,
            Authentication auth) {
        try {
            AgendamentoDTO atualizado = agendamentoService.atualizarAgendamentoPaciente(id, dto);

            // 🔒 Se for paciente, valida se está atualizando o próprio agendamento
            if (auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {

                String emailLogado = auth.getName();
                String emailPaciente = agendamentoService.buscarEmailPorIdPaciente(atualizado.getPacienteId());

                if (!emailLogado.equals(emailPaciente)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Você não tem permissão para atualizarAgendamentoPaciente este agendamento.");
                }
            }

            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizarAgendamentoPaciente agendamento: " + e.getMessage());
        }
    }


    // 🔹 Deletar agendamento — apenas ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar agendamento", description = "Remove um agendamento pelo ID (ADMIN)")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            agendamentoService.deletarAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar agendamento: " + e.getMessage());
        }
    }

    //  Alterar status do comprovante — apenas ADMIN
    @PatchMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar status do comprovante", description = "Altera o status do comprovante do agendamento (ADMIN)")
    public ResponseEntity<?> atualizarAgendamentoPacienteStatus(@PathVariable Long id,
                                                                @RequestBody StatusComprovanteEnum novoStatus) {
        try {
            String resposta = agendamentoService.alterarStatusComprovante(id, novoStatus);
            return ResponseEntity.ok(resposta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/motorista/{motoristaId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atribuir motorista ao agendamento", description = "Associa um motorista existente ao agendamento (ADMIN)")
    public ResponseEntity<?> atribuirMotorista(@PathVariable Long id, @PathVariable Long motoristaId) {
        try {
            AgendamentoDTO atualizado = agendamentoService.atribuirMotorista(id, motoristaId);
            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atribuir motorista: " + e.getMessage());
        }
    }

}
