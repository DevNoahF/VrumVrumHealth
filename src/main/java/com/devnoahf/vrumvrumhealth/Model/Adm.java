package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Role;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false, unique = true)
    private String email;


    private String senha_hash;

    @Column(nullable = false, updatable = false)
    private LocalDate  data_criacao;
    private LocalDate data_atualizacao;

    @Enumerated(EnumType.STRING)
    private Role role;

    public CharSequence getSenha_hash() {
    }

    public void setSenha_hash(String senhaCriptografada) {
    }
}
