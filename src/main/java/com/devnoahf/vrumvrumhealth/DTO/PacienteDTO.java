package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimentoEnum;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PacienteDTO {
    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private String cpf;
    private RoleEnum roleEnum;
    private Date dataNascimento;
    private TipoAtendimentoEnum tipoAtendimentoEnum;
    private String telefone;
    private String cep;
    private String rua;
    private String bairro;
    private Integer numero;
    private FrequenciaEnum frequenciaEnum;
}
