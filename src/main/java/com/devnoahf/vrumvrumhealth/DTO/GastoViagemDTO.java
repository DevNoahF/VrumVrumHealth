package com.devnoahf.vrumvrumhealth.DTO;

import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GastoViagemDTO {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Long diarioBordoId;

}
