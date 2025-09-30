package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    // esse aqui converte de Entity para DTO
    public AgendamentoDTO toDTO(Agendamento agendamento) {
        if (agendamento == null) return null;

        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataConsulta(agendamento.getData_consulta().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate()); // converte Date para LocalDate
        dto.setHoraConsulta(agendamento.getHora_consulta());
        dto.setComprovante(agendamento.getComprovante());
        dto.setLocalAtendimento(agendamento.getLocal_atendimento());
        dto.setEstado(agendamento.getEstado());
        dto.setRetornoCasa(agendamento.getRetorno_casa());
        dto.setDataCriacao(agendamento.getData_criacao());
        dto.setDataAtualizacao(agendamento.getData_atualizacao());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
        }

        return dto;
    }

    // Esse aqui para converter de DTO para Entity
    public Agendamento toEntity(AgendamentoDTO dto, Paciente paciente) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setData_consulta(java.sql.Date.valueOf(dto.getDataConsulta()));
        agendamento.setHora_consulta(dto.getHoraConsulta());
        agendamento.setComprovante(dto.getComprovante());
        agendamento.setLocal_atendimento(dto.getLocalAtendimento());
        agendamento.setEstado(dto.getEstado());
        agendamento.setRetorno_casa(dto.getRetornoCasa());
        agendamento.setData_criacao(dto.getDataCriacao());
        agendamento.setData_atualizacao(dto.getDataAtualizacao());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        return agendamento;
    }
}
