package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;

import java.time.Instant;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente { // TODO: possivel implementação de imagens de perfil
 
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;


    @Column(nullable = false, unique = true)
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, length = 2, columnDefinition = "INT")
    private int ddd;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;


    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now(); // opcional
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Garantir que o roleEnum seja sempre PACIENTE
    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = RoleEnum.PACIENTE;
    }
}
