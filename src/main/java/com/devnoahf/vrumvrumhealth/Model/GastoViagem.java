package com.devnoahf.vrumvrumhealth.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "gasto_viagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GastoViagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@lob -> caso não funcione o collumn usar esse.
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "0.00")
    private BigDecimal valor;


    @OneToMany
    @JoinColumn()
    private DiarioBordo diarioBordo;

}
