package com.devnoahf.vrumvrumhealth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PacientePerfilDTO {

    private String cpf;
    private Date dataNascimento;
    private String cep;
    private String rua;
    private String bairro;
    private int ddd;
    private String telefone;

}
