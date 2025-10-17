package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Repository.DiarioBordoRepository;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
@RequiredArgsConstructor
@UtilityClass
public class MotoristaMapper {


    public static MotoristaDTO toDTO(Motorista motorista){
        return MotoristaDTO.builder()
                .id(motorista.getId())
                .nome(motorista.getNome())
                .email(motorista.getEmail())
                .cpf(motorista.getCpf())
                .senha(motorista.getSenha())
                .telefone(motorista.getTelefone())
                .roleEnum(motorista.getRoleEnum())
                .diarioBordo(motorista.getDiariosBordo())
                .build();
    }

    public Motorista toEntity(MotoristaDTO dto, List<DiarioBordo> diarios) {
        Motorista motorista = Motorista.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .senha(dto.getSenha())
                .telefone(dto.getTelefone())
                .build();

        // Vincula cada diário ao motorista
        diarios.forEach(diario -> diario.setMotoristas(motorista));

        motorista.setDiariosBordo(diarios);

        return motorista;
    }


}
