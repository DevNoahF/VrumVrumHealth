package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.StatusComprovanteEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "tb_agendamento")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_consulta")
    private LocalDate dataConsulta;

    @Column(nullable = false, name = "hora_consulta")
    private LocalTime horaConsulta;

    private String comprovante;

    @Enumerated(EnumType.STRING)
    private LocalAtendimentoEnum localAtendimento;

    @Enumerated(EnumType.STRING)
    private StatusComprovanteEnum statusComprovanteEnum;

    @Column(nullable = false, name = "retorno_casa")
    private Boolean retornoCasa;

    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;


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
