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
        dto.setDataConsulta(agendamento.getDataConsulta());
        dto.setHoraConsulta(agendamento.getHoraConsulta());
        dto.setComprovante(agendamento.getComprovante());
        dto.setLocalAtendimentoEnum(agendamento.getLocal_atendimento());
        dto.setStatusEnum(agendamento.getStatusEnum());
        dto.setRetornoCasa(agendamento.getRetorno_casa());
        dto.setTratamentoContinuo(agendamento.getTratamentoContinuo());
        dto.setFrequencia(agendamento.getFrequencia());

        if (agendamento.getPaciente() != null) {
            dto.setPacienteId(agendamento.getPaciente().getId());
            //dto.setImagemPaciente(agendamento.getPaciente().getImagem()); implementar imagem paciente
        }

        return dto;
    }


    // Esse aqui para converter de DTO para Entity
    public Agendamento toEntity(AgendamentoDTO dto, Paciente paciente) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setDataConsulta(dto.getDataConsulta());
        agendamento.setHoraConsulta(dto.getHoraConsulta());
        agendamento.setComprovante(dto.getComprovante());
        agendamento.setLocal_atendimento(dto.getLocalAtendimentoEnum());
        agendamento.setStatusEnum(dto.getStatusEnum());
        agendamento.setRetorno_casa(dto.getRetornoCasa());
        agendamento.setTratamentoContinuo(dto.getTratamentoContinuo());
        agendamento.setFrequencia(dto.getFrequencia());

        // Associa paciente se passado
        agendamento.setPaciente(paciente);

        return agendamento;
    }

}
