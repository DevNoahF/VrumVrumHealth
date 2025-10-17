package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;

import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;
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
    private Date dataNascimento;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String senha;


    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    private TipoAtendimentoEnum tipoAtendimentoEnum;


    @Enumerated(EnumType.STRING)
    private FrequenciaEnum frequenciaEnum;


    @Column(nullable = false, updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;


    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // opcional
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
