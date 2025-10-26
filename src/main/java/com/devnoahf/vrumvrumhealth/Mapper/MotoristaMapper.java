package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Builder
@UtilityClass
public class MotoristaMapper {

private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static MotoristaDTO toDTO(Motorista motorista){
        return MotoristaDTO.builder()
                // nao settar adm no mapper, pq é desnecessario
                .nome(motorista.getNome())
                .email(motorista.getEmail())
                .telefone(motorista.getTelefone())
                .build();
    }

    public Motorista toEntity(MotoristaDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        return Motorista.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .senha(senhaCriptografada)
                .build();
    }


}
