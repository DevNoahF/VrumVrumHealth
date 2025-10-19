package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import jakarta.validation.constraints.NotBlank;
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

    // comentado NotBlank para facilitar testes iniciais
    private Long id;
//    @NotBlank
    private String nome;
//    @NotBlank
    private String cpf;
//    @NotBlank
    private String email;
//    @NotBlank
    private String senha;
//    @NotBlank
    private String telefone;
//    @NotBlank
    private List<Long> diarioBordo;

}
