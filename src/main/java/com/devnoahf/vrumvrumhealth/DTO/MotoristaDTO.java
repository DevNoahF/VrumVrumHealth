package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
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
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private RoleEnum roleEnum;
    private List<Long> diarioBordo;

}
