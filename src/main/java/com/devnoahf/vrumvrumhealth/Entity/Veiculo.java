package com.devnoahf.vrumvrumhealth.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculo")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Veiculo {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    private String modelo;

    private int capacidade;


}
