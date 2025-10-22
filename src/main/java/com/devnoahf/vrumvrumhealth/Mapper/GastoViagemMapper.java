package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.GastoViagemDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Model.GastoViagem;
import org.springframework.stereotype.Component;

@Component
public class GastoViagemMapper {

    // DTO para o Entity
    public GastoViagem toEntity(GastoViagemDTO dto, DiarioBordo diarioBordo) {
        if (dto.getValor() == null) {
            throw new IllegalArgumentException("O valor do gasto não pode ser nulo.");
        }

        GastoViagem entity = new GastoViagem();
        entity.setDescricao(dto.getDescricao());
        entity.setValor(dto.getValor());
        entity.setDiarioBordo(diarioBordo);
        return entity;
    }

    // Entity para o  DTO
    public GastoViagemDTO toDTO(GastoViagem entity) {
        GastoViagemDTO dto = new GastoViagemDTO();
        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());
        dto.setValor(entity.getValor());

        // (NOAH) = Evita NullPointerException caso o DiarioBordo seja nulo
        dto.setDiarioBordoId(
                entity.getDiarioBordo() != null ? entity.getDiarioBordo().getId() : null
        );

        return dto;
    }
}
