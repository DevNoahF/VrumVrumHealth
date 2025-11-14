package com.devnoahf.vrumvrumhealth.mapper;

import com.devnoahf.vrumvrumhealth.dto.VeiculoDTO;
import com.devnoahf.vrumvrumhealth.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    // esse converte de Entity para DTO
    public VeiculoDTO toDTO(Veiculo veiculo) {
        if (veiculo == null) return null;

        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setModelo(veiculo.getModelo());
        dto.setCapacidade(veiculo.getCapacidade());

        return dto;
    }

    // e esse converte de DTO para Entity
    public Veiculo toEntity(VeiculoDTO dto) {
        if (dto == null) return null;

        Veiculo veiculo = new Veiculo();
        veiculo.setId(dto.getId());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setCapacidade(dto.getCapacidade());

        return veiculo;
    }
}
