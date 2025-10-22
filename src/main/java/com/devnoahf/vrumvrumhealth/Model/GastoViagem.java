package com.devnoahf.vrumvrumhealth.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tb_GastoViagem")
@Data

public class GastoViagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@lob -> caso não funcione o collumn usar esse.
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "0.00")
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "diario_bordo_id", nullable = false)
    private DiarioBordo diarioBordo;

}
