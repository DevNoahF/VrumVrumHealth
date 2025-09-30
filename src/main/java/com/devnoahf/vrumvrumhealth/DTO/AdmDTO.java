package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmDTO {
    private Long id;
    private String nome;
    private String matricula;
    private String email;
    private String senhaHash;
    private Role role;


}
