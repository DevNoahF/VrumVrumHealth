package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.Frequencia;
import com.devnoahf.vrumvrumhealth.Enum.Role;
import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimento;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
    private Role role;
    private LocalDate dataNascimento;
    private TipoAtendimento tipoAtendimento;
    private String telefone;
    private String cep;
    private String rua;
    private String bairro;
    private Integer numero;
    private Frequencia frequencia;
}
