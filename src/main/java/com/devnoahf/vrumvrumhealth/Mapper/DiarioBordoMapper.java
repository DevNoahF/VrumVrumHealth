package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import org.springframework.stereotype.Component;

@Component
public class DiarioBordoMapper {

    public DiarioBordoDTO toDTO(DiarioBordo entity) {
        DiarioBordoDTO dto = new DiarioBordoDTO();
        dto.setId(entity.getId());
        dto.setQuilometragemInicial(entity.getQuilometragemInicial());
        dto.setQuilometragemFinal(entity.getQuilometragemFinal());
        dto.setMotorista(entity.getMotorista());
        dto.setVeiculo(entity.getVeiculo());
        dto.setTransporte(entity.getTransporte());
        dto.setObservacoes(entity.getObservacoes());
        return dto;
    }

    public DiarioBordo toEntity(DiarioBordoDTO dto) {
        return DiarioBordo.builder()
                .id(dto.getId())
                .quilometragemInicial(dto.getQuilometragemInicial())
                .quilometragemFinal(dto.getQuilometragemFinal())
                .motorista(dto.getMotorista())
                .veiculo(dto.getVeiculo())
                .transporte(dto.getTransporte())
                .observacoes(dto.getObservacoes())
                .build();
    }
}
