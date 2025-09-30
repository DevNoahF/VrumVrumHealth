package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.Estado;
import com.devnoahf.vrumvrumhealth.Enum.LocalAtendimento;
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
    private LocalAtendimento localAtendimento;
    private Estado estado;
    private Boolean retornoCasa;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private Long pacienteId; // referenciando apenas o id do paciente
}
