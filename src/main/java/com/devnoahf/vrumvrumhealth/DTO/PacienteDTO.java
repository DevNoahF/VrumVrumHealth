package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimentoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Nome é obrigatorio!")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres!")
    private String nome;

    @Email(message = "Email inválido!")
    @NotBlank(message = "Email é obrigatorio!")
    private String email;

    @NotBlank(message = "Senha é obrigatoria!")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "CPF é obrigatorio!")
    @Size(min = 11,max = 11, message = "CPF deve ter 11 digitos!")
    private String cpf;

    @NotBlank(message = "Data de nascimento é obrigatorio!")
    private Date dataNascimento;

    @NotBlank(message = "Informe o DDD!")
    @Size(min = 2, max = 2, message = "DDD deve ter 2 digitos!")
    private int ddd;

    @NotBlank(message = "Telefone é obrigatorio!")
    @Size(min = 8, max = 9, message = "Telefone deve ter entre 8 e 9 digitos!")
    private String telefone;

    @NotBlank(message = "cep é obrigatorio!")
    private String cep;

    @NotBlank(message = "A rua é obrigatoria!")
    private String rua;

    @NotBlank(message = "O bairro é obrigatorio!")
    private String bairro;

    @NotBlank(message = "O numero da residência é obrigatorio!")
    private Integer numero;
}
