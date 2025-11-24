package com.devnoahf.vrumvrumhealth.mapper;

import com.devnoahf.vrumvrumhealth.dto.TransporteDTO;
import com.devnoahf.vrumvrumhealth.model.Agendamento;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.model.Transporte;
import com.devnoahf.vrumvrumhealth.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class TransporteMapper {

    // esse converte de Entity para DTO
    public TransporteDTO toDTO(Transporte transporte) {
        if (transporte == null) return null;

        TransporteDTO dto = new TransporteDTO();
        dto.setId(transporte.getId());
        dto.setHorarioSaida(transporte.getHorarioSaida());

        if (transporte.getVeiculo() != null) {
            dto.setVeiculoId(transporte.getVeiculo().getId());
            dto.setVeiculoPlaca(transporte.getVeiculo().getPlaca());
            dto.setVeiculoModelo(transporte.getVeiculo().getModelo());
        }

        if (transporte.getAgendamento() != null) {
            dto.setAgendamentoId(transporte.getAgendamento().getId());
            dto.setLocalAtendimento(transporte.getAgendamento().getLocalAtendimentoEnum() != null ? transporte.getAgendamento().getLocalAtendimentoEnum().getLocalAtendimento() : null);
            dto.setDataConsulta(transporte.getAgendamento().getDataConsulta());
            dto.setHoraConsulta(transporte.getAgendamento().getHoraConsulta());
            dto.setRetornoCasa(transporte.getAgendamento().getRetornoCasa());
            dto.setAcompanhante(transporte.getAgendamento().getAcompanhante());
        }

        if (transporte.getMotorista() != null) {
            dto.setMotoristaId(transporte.getMotorista().getId());
            dto.setMotoristaNome(transporte.getMotorista().getNome());
        }

        if (transporte.getPaciente() != null) {
            dto.setPacienteId(transporte.getPaciente().getId());
            dto.setPacienteNome(transporte.getPaciente().getNome());
        }


        return dto;
    }

    // e esse converte de DTO para Entity
    //TODO: NECESSARIO REFATORAR PARA RETORNAR APENAS O NECESSARIO
    public Transporte toEntity(TransporteDTO dto,
                               Veiculo veiculo,
                               Agendamento agendamento,
                               Motorista motorista,
                               Paciente paciente) {
        if (dto == null) return null;

        Transporte transporte = new Transporte();
        transporte.setId(dto.getId());
        transporte.setHorarioSaida(dto.getHorarioSaida());
        transporte.setVeiculo(veiculo);
        transporte.setAgendamento(agendamento);
        transporte.setMotorista(motorista);
        transporte.setPaciente(paciente);

        return transporte;
    }
}
