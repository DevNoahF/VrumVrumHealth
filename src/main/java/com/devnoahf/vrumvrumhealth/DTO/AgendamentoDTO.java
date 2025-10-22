package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
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
    private String imagemComprovante;
    private Boolean acompanhante;
    private LocalAtendimentoEnum localAtendimentoEnum;
    private StatusEnum statusEnum;
    private Boolean retornoCasa;
    private Long pacienteId; // referenciando apenas o id do paciente
    //private String imagemPaciente; aqui para retornar imagem
}
