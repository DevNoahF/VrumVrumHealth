package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.TransporteDTO;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Transporte;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class TransporteMapper {

    // esse converte de Entity para DTO
    public TransporteDTO toDTO(Transporte transporte) {
        if (transporte == null) return null;

        TransporteDTO dto = new TransporteDTO();
        dto.setId(transporte.getId());
        dto.setHorarioSaida(transporte.getHorarioSaida());
        dto.setDataCriacao(transporte.getData_criacao());
        dto.setDataAtualizacao(transporte.getData_atualizacao());

        if (transporte.getVeiculo() != null) {
            dto.setVeiculoId(transporte.getVeiculo().getId());
        }

        if (transporte.getAgendamento() != null) {
            dto.setAgendamentoId(transporte.getAgendamento().getId());
        }

        return dto;
    }

    // e esse converte de DTO para Entity
    public Transporte toEntity(TransporteDTO dto, Veiculo veiculo, Agendamento agendamento) {
        if (dto == null) return null;

        Transporte transporte = new Transporte();
        transporte.setId(dto.getId());
        transporte.setHorarioSaida(dto.getHorarioSaida());
        transporte.setData_criacao(dto.getDataCriacao());
        transporte.setData_atualizacao(dto.getDataAtualizacao());

        transporte.setVeiculo(veiculo);
        transporte.setAgendamento(agendamento);

        return transporte;
    }
}
