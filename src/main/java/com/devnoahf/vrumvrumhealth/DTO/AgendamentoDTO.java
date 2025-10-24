package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.StatusComprovanteEnum;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AgendamentoDTO {
    private Long id;
    private LocalDate dataConsulta;
    private Time horaConsulta;
    private String comprovante;
    private LocalAtendimentoEnum localAtendimento;
    private StatusComprovanteEnum statusComprovanteEnum;
    private Boolean retornoCasa;
    private Long pacienteId;
}
