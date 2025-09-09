package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_adm")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Adm {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String matricula;

    private String email;

    private String senha;

    // utilização LocalDate para facilitar a manipulação de datas para testes
    private LocalDate  data_criacao; //=localDate.now();

    private LocalDate data_atualizacao; //=localDate.now();

    private Role role;

}
