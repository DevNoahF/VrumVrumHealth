package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import lombok.Builder;
import lombok.experimental.UtilityClass;

@Builder
@UtilityClass
public class DiarioBordoMapper {


    public DiarioBordo toDTO(DiarioBordo diarioBordo) {
        return DiarioBordo.builder()
                .id(diarioBordo.getId())
                .quilometragemInicial(diarioBordo.getQuilometragemInicial())
                .quilometragemFinal(diarioBordo.getQuilometragemFinal())
                .observacoes(diarioBordo.getObservacoes())
                .motorista(diarioBordo.getMotorista())
                .veiculos(diarioBordo.getVeiculos())
                .transporte(diarioBordo.getTransporte())
                .build();
    }


    public DiarioBordoDTO toEntity(DiarioBordoDTO diarioBordo) {
        return DiarioBordoDTO.builder()
                .id(diarioBordo.getId())
                .quilometragemInicial(diarioBordo.getQuilometragemInicial())
                .quilometragemFinal(diarioBordo.getQuilometragemFinal())
                .observacoes(diarioBordo.getObservacoes())
                .motorista(diarioBordo.getMotorista())
                .veiculo(diarioBordo.getVeiculo())
                .transporte(diarioBordo.getTransporte())
                .build();


    }


}
