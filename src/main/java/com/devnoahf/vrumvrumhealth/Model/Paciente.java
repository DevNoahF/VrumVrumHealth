package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Frequencia;
import com.devnoahf.vrumvrumhealth.Enum.Role;
import com.devnoahf.vrumvrumhealth.Enum.Tipo_atendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "paciente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_nascimento;

    @Column(nullable = false, unique = true)
    private String email;

    private String senha_hash;

    private String telefone;

    private String cep;

    private String rua;

    private String bairro;

    private Integer numero;

    @Enumerated(EnumType.STRING)
    private Tipo_atendimento tipo_atendimento;

    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;


    @Column(nullable = false, updatable = false)
    private LocalDateTime data_criacao;

    private LocalDateTime data_atualizacao;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "paciente_id", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Agendamento> agendamentos;


    public CharSequence getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }
}
