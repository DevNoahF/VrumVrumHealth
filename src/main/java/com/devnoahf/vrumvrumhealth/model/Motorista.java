package com.devnoahf.vrumvrumhealth.model;

import com.devnoahf.vrumvrumhealth.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "motorista")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Motorista {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String ddd;

    @Column(length = 20, nullable = false)
    private String telefone;


    @OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiarioBordo> diariosDeBordo;


    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public Motorista(RoleEnum roleEnum) {
        this.setRoleEnum(RoleEnum.MOTORISTA);
    }


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
}
