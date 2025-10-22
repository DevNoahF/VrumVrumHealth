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
        dto.setDataConsulta(agendamento.getData_consulta());
        dto.setHoraConsulta(agendamento.getHora_consulta());
        dto.setLocalAtendimentoEnum(agendamento.getLocal_atendimento());
        dto.setStatusEnum(agendamento.getStatusEnum());
        dto.setRetornoCasa(agendamento.getRetorno_casa());
        dto.setAcompanhante(agendamento.getAcompanhante());
        dto.setImagemComprovante(agendamento.getImagemComprovante());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
            //dto.setImagemPaciente(agendamento.getPaciente().getImagem()); implementar imagem paciente
        }

        return dto;
    }

    // Converte DTO → Entity
    public Agendamento toEntity(AgendamentoDTO dto, Paciente paciente) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setData_consulta(dto.getDataConsulta());
        agendamento.setHora_consulta(dto.getHoraConsulta());
        agendamento.setLocal_atendimento(dto.getLocalAtendimentoEnum());
        agendamento.setStatusEnum(dto.getStatusEnum());
        agendamento.setRetorno_casa(dto.getRetornoCasa());
        agendamento.setAcompanhante(dto.getAcompanhante());
        agendamento.setImagemComprovante(dto.getImagemComprovante());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        return agendamento;
    }

}
