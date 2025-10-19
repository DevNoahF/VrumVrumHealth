package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Model.Transporte;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
