package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Future(message = "A hora da consulta deve ser no futuro.")
    private LocalTime horaConsulta;
    @NotBlank(message = "É obrigatorio o envio do comprovante")
    private String comprovante;
    @NotBlank(message = "É obrigatorio selecionar o local de atendimento")
    private LocalAtendimentoEnum localAtendimentoEnum;

    private StatusEnum statusEnum;
    @NotBlank(message = "É obrigatorio informar se haverá retorno para casa")
    private Boolean retornoCasa;

    @NotNull(message = "É obrigatorio informar se é tratamento contionuo ou não")
    private Boolean tratamentoContinuo;

    private FrequenciaEnum frequencia;

    private Long pacienteId;
}
