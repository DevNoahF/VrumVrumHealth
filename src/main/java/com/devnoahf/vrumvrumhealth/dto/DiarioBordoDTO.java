package com.devnoahf.vrumvrumhealth.dto;

import com.devnoahf.vrumvrumhealth.model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.model.Transporte;
import com.devnoahf.vrumvrumhealth.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor

@Getter
@Setter
@Component
public class DiarioBordoDTO {
    private Long id;
    @NotBlank(message = "É obrigatorio informar a quilometragem inicial")
    private BigDecimal quilometragemInicial;
    @NotBlank(message = "É obrigatorio informar a quilometragem final")
    private BigDecimal quilometragemFinal;
    private String observacoes;
    private Motorista motorista;
    private Veiculo veiculo;
    private Transporte transporte;

}




