package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class AgendamentoMapper {

    // Converte Entity → DTO
    public AgendamentoDTO toDTO(Agendamento agendamento) {
        if (agendamento == null) return null;

        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataConsulta(
                agendamento.getData_consulta()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
        dto.setHoraConsulta(agendamento.getHora_consulta());
        dto.setComprovante(agendamento.getComprovante());
        dto.setLocalAtendimentoEnum(agendamento.getLocal_atendimento());
        dto.setStatusEnum(agendamento.getStatusEnum());
        dto.setRetornoCasa(agendamento.getRetorno_casa());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
            dto.setImagemPaciente(agendamento.getPaciente().getImagem());
        }

        return dto;
    }

    // Converte DTO → Entity
    public Agendamento toEntity(AgendamentoDTO dto, Paciente paciente) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setData_consulta(java.sql.Date.valueOf(dto.getDataConsulta()));
        agendamento.setHora_consulta(dto.getHoraConsulta());
        agendamento.setComprovante(dto.getComprovante());
        agendamento.setLocal_atendimento(dto.getLocalAtendimentoEnum());
        agendamento.setStatusEnum(dto.getStatusEnum());
        agendamento.setRetorno_casa(dto.getRetornoCasa());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        return agendamento;
    }

}
