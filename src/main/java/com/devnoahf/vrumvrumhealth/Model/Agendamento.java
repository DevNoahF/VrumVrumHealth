package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Estado;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
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

    private Date data_consulta;

    private Time hora_consulta;

    private String comprovante;

    @Enumerated(EnumType.STRING)
    private LocalAtendimento local_atendimento;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Boolean retorno_casa;

    @Column(nullable = false, updatable = false)
    private LocalDate data_criacao;

    private LocalDate data_atualizacao;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

}
