package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AgendamentoDTO {
    private Long id;
    @Future(message = "A data da consulta deve ser no futuro.")
    private LocalDate dataConsulta;
    @Time
    private LocalTime horaConsulta;
    private String comprovante;
    private LocalAtendimentoEnum localAtendimentoEnum;
    private StatusEnum statusEnum;
    private Boolean retornoCasa;
    private Long pacienteId; // referenciando apenas o id do paciente
}
