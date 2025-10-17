package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class DiarioBordoDTO {
    private Long id;
    private BigDecimal quilometragemInicial;
    private BigDecimal quilometragemFinal;
    private String observacoes;
    private Motorista motorista;
    private Veiculo veiculo;
}
