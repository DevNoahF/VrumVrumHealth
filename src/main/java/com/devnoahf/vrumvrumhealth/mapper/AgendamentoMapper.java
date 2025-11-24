package com.devnoahf.vrumvrumhealth.mapper;

import com.devnoahf.vrumvrumhealth.dto.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.enums.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.model.Agendamento;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    // Request
    public AgendamentoDTO request(Agendamento agendamento) {
        if (agendamento == null) return null;
        agendamento.setFrequencia(agendamento.getFrequencia() == null ? FrequenciaEnum.NENHUMA : agendamento.getFrequencia());

        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataConsulta(agendamento.getDataConsulta());
        dto.setHoraConsulta(agendamento.getHoraConsulta());
        dto.setComprovante(agendamento.getComprovante());
        dto.setLocalAtendimentoEnum(agendamento.getLocalAtendimentoEnum());
        dto.setStatusComprovanteEnum(agendamento.getStatusComprovanteEnum());
        dto.setRetornoCasa(agendamento.getRetornoCasa());
        dto.setTratamentoContinuo(agendamento.getTratamentoContinuo());
        dto.setFrequencia(agendamento.getFrequencia());
        dto.setAcompanhante(agendamento.getAcompanhante());
        dto.setTipoAtendimentoEnum(agendamento.getTipoAtendimentoEnum());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
        }
        // Mapeia motorista se houver
        if (agendamento.getMotorista() != null) {
            dto.setMotoristaId(agendamento.getMotorista().getId());
        }
        return dto;
    }


    // Response
    public Agendamento response(AgendamentoDTO dto, Paciente paciente) {
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
        agendamento.setTipoAtendimentoEnum(dto.getTipoAtendimentoEnum());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        // motoristaId será associado em endpoint específico de ADMIN; ignorar aqui.
        return agendamento;
    }

}
