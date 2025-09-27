package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Frequencia;
import com.devnoahf.vrumvrumhealth.Enum.Role;

import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Entity
@Table(name = "paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;


    @Column(nullable = false, unique = true)
    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String email;


    private String senhaHash;


    private String telefone;

    private String cep;

    private String rua;

    private String bairro;

    private Integer numero;

    @Enumerated(EnumType.STRING)
    private TipoAtendimento tipoAtendimento;


    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;


    @Column(nullable = false, updatable = false)
    private LocalDateTime data_criacao;

    private LocalDateTime data_atualizacao;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;


    @Enumerated(EnumType.STRING)
    private Role roles;


}
