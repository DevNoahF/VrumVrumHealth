package com.devnoahf.vrumvrumhealth.mapper;

import com.devnoahf.vrumvrumhealth.dto.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.model.Agendamento;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    // Request
    public AgendamentoDTO toDTO(Agendamento agendamento) {
        if (agendamento == null) return null;

        AgendamentoDTO dto = new AgendamentoDTO();

        dto.setId(agendamento.getId());
        dto.setStatusComprovanteEnum(agendamento.getStatusComprovanteEnum());
        dto.setDataConsulta(agendamento.getDataConsulta());
        dto.setHoraConsulta(agendamento.getHoraConsulta());
        dto.setComprovante(agendamento.getComprovante());
        dto.setLocalAtendimentoEnum(agendamento.getLocalAtendimentoEnum());
        dto.setRetornoCasa(agendamento.getRetornoCasa());
        dto.setTratamentoContinuo(agendamento.getTratamentoContinuo());
        dto.setFrequencia(agendamento.getFrequencia());
        dto.setAcompanhante(agendamento.getAcompanhante());
        dto.setTipoAtendimentoEnum(agendamento.getTipoAtendimentoEnum());
        dto.setTratamentoContinuo(agendamento.getTratamentoContinuo());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
        }

        return dto;
    }


    // Response
    public Agendamento toEntity(AgendamentoDTO dto, Paciente paciente) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setDataConsulta(dto.getDataConsulta());
        agendamento.setHoraConsulta(dto.getHoraConsulta());
        agendamento.setComprovante(dto.getComprovante());
        agendamento.setLocalAtendimentoEnum(dto.getLocalAtendimentoEnum());
        agendamento.setStatusComprovanteEnum(dto.getStatusComprovanteEnum());
        agendamento.setRetornoCasa(dto.getRetornoCasa());
        agendamento.setTratamentoContinuo(dto.getTratamentoContinuo());
        agendamento.setFrequencia(dto.getFrequencia());
        agendamento.setAcompanhante(dto.getAcompanhante());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        return agendamento;
    }

}
