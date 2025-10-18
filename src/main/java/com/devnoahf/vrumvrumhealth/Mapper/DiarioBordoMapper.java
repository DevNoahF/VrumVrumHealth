package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
@Component
public class DiarioBordoMapper {


    public DiarioBordo toDTO(DiarioBordo diarioBordo) {
        return DiarioBordo.builder()
                .id(diarioBordo.getId())
                .quilometragemInicial(diarioBordo.getQuilometragemInicial())
                .quilometragemFinal(diarioBordo.getQuilometragemFinal())
                .observacoes(diarioBordo.getObservacoes())
                .motoristas(diarioBordo.getMotoristas())
                .veiculos(diarioBordo.getVeiculos())
                .build();
    }


    public DiarioBordoDTO toEntity(DiarioBordo diarioBordo) {
        return DiarioBordoDTO.builder()
                .id(diarioBordo.getId())
                .quilometragemInicial(diarioBordo.getQuilometragemInicial())
                .quilometragemFinal(diarioBordo.getQuilometragemFinal())
                .observacoes(diarioBordo.getObservacoes())
                .motorista(diarioBordo.getMotoristas())
                .veiculo(diarioBordo.getVeiculos())
                .build();
    }


}
