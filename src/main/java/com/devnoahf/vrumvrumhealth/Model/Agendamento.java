package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "agendamento")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_consulta")
    private LocalDate dataConsulta;

    @Column(name = "hora_consulta")
    private LocalTime horaConsulta;

    @Column(length = 1000)
    private String imagemComprovante;

    //@Column(length = 1000)
    //private String imagem; com isso aqui vai fazer o paciente ter imagem

    @Enumerated(EnumType.STRING)
    private LocalAtendimentoEnum local_atendimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    private Boolean retorno_casa;

    private Boolean tratamentoContinuo;

    private FrequenciaEnum frequencia;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;



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
