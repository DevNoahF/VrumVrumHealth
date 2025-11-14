package com.devnoahf.vrumvrumhealth.dto;

import com.devnoahf.vrumvrumhealth.enums.FrequenciaEnum;
import com.devnoahf.vrumvrumhealth.enums.LocalAtendimentoEnum;
import com.devnoahf.vrumvrumhealth.enums.StatusComprovanteEnum;
import com.devnoahf.vrumvrumhealth.enums.TipoAtendimentoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
    // Use ISO time format HH:mm for requests/responses
    @NotNull(message = "A hora da consulta é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaConsulta;
    @NotBlank(message = "É obrigatorio o envio do comprovante")
    private String comprovante;
    @NotNull(message = "É obrigatorio selecionar o local de atendimento")
    private LocalAtendimentoEnum localAtendimentoEnum;

    private StatusComprovanteEnum statusComprovanteEnum; // status do comprovante
    @NotNull(message = "É obrigatorio informar se haverá retorno para casa")
    private Boolean retornoCasa;

    @NotNull(message = "É obrigatorio informar se é tratamento contionuo ou não")
    private Boolean tratamentoContinuo;

    @NotNull(message = "É obrigatorio selecionar o tipo de atendimento")
    private TipoAtendimentoEnum tipoAtendimentoEnum;

    private FrequenciaEnum frequencia;

    @NotNull(message = "É obrigatorio informar se haverá acompanhante")
    private Boolean acompanhante;

    private Long pacienteId;
}
