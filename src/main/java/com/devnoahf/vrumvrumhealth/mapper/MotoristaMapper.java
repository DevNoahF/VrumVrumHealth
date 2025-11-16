package com.devnoahf.vrumvrumhealth.mapper;

import com.devnoahf.vrumvrumhealth.dto.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.enums.RoleEnum;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Objects;


@UtilityClass
public class MotoristaMapper {

private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static MotoristaDTO toDTO(Motorista motorista){
        // Map all public fields except senha
        MotoristaDTO.MotoristaDTOBuilder builder = MotoristaDTO.builder()
                .id(motorista.getId())
                .nome(motorista.getNome())
                .cpf(motorista.getCpf())
                .email(motorista.getEmail())
                .ddd(motorista.getDdd())
                .telefone(motorista.getTelefone())
                .dataNascimento(motorista.getDataNascimento());

        // Do not set senha (leave null)
        return builder.build();
    }

    public Motorista toEntityUpdate(Motorista existente, MotoristaDTO dto) {
        // Atualiza os campos que sempre vêm
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setCpf(dto.getCpf());
        existente.setTelefone(dto.getTelefone());

        // Atualiza a senha somente se veio no DTO
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return existente;
    }


    public Motorista toEntity(MotoristaDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());


        return Motorista.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .senha(senhaCriptografada)
                .ddd(dto.getDdd())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDataNascimento())
                .roleEnum(RoleEnum.MOTORISTA)
                .build();
    }


}
