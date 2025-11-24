package com.devnoahf.vrumvrumhealth.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TransporteDTO {
    private Long id;
    private LocalTime horarioSaida;
    private Long veiculoId;
    private Long agendamentoId;
    private Long motoristaId;
    private Long pacienteId;

    // Derivados
    private String pacienteNome;
    private String motoristaNome;
    private String veiculoPlaca;
    private String veiculoModelo;
    private String localAtendimento;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private Boolean retornoCasa;
    private Boolean acompanhante;
}
