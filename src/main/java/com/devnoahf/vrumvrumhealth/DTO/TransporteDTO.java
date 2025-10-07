package com.devnoahf.vrumvrumhealth.DTO;

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
public class TransporteDTO {
    private Long id;

    private LocalTime horarioSaida;
    private Long veiculoId;
    private Long agendamentoId;
}
