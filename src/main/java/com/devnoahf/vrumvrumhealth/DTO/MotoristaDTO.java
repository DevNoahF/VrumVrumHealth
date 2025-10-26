package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MotoristaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 digitos")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "Informe o DDD!")
    @Size(min = 2, max = 2, message = "DDD deve ter 2 digitos!")
    private String ddd;

    @NotBlank(message = "Telefone é obrigatorio!")
    @Size(min = 8, max = 9, message = "Telefone deve ter entre 8 e 9 digitos!")
    private String telefone;


}
