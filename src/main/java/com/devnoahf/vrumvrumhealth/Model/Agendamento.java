package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDate data_consulta;

    private Time hora_consulta;

    @Column(length = 1000)
    private String imagemComprovante;

    //@Column(length = 1000)
    //private String imagem; com isso aqui vai fazer o paciente ter imagem

    @Enumerated(EnumType.STRING)
    private LocalAtendimentoEnum local_atendimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    private Boolean retorno_casa;

    private Boolean acompanhante;

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
