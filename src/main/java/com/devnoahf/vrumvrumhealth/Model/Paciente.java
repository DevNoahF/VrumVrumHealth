package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;

import com.devnoahf.vrumvrumhealth.Enum.TipoAtendimentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import java.time.LocalDate;
import lombok.Data; // foi necessário para criar o getter e setter automático

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data //minha modificação Gustavo junto com o lombok data
@Table(name = "tb_paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String imagem;

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
