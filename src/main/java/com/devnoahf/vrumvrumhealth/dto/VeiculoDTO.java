package com.devnoahf.vrumvrumhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class VeiculoDTO {
    private Long id;
    private String placa;
    private String modelo;
    private int capacidade;
}
