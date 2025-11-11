package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@UtilityClass
public class MotoristaMapper {

private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static MotoristaDTO toDTO(Motorista motorista){
        String dataNascimentoStr = new SimpleDateFormat("yyyy-MM-dd").format(motorista.getDataNascimento());
        return MotoristaDTO.builder()
                // nao settar adm no mapper, pq é desnecessario
                .nome(motorista.getNome())
                .email(motorista.getEmail())
                .telefone(motorista.getTelefone())
                .dataNascimento(dataNascimentoStr)
                .build();
    }

    public Motorista toEntityUpdate(Motorista existente, MotoristaDTO dto) {
        // Atualiza os campos que sempre vêm
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setCpf(dto.getCpf());
        existente.setTelefone(dto.getTelefone());

        // Atualiza a data de nascimento se válida
        try {
            Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDataNascimento());
            existente.setDataNascimento(dataNascimento);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Data de nascimento inválida. Use formato yyyy-MM-dd");
        }

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

        Date dataNascimento = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); // ajuste conforme seu JSON
            dataNascimento = formato.parse(dto.getDataNascimento());
        } catch (ParseException e) {
            e.printStackTrace(); // ou lance RuntimeException se quiser falhar
        }

        return Motorista.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .ddd(dto.getDdd())
                .telefone(dto.getTelefone())
                .dataNascimento(dataNascimento)
                .roleEnum(RoleEnum.MOTORISTA)
                .build();
    }


}
