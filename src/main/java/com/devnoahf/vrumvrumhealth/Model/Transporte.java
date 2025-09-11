package com.devnoahf.vrumvrumhealth.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.LocalDateTime;

@Entity
@Table(name = "transporte")
@Data
public class Transporte {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	private Long veiculoId;

	// NOTE: it will be needed to format
	// 'cause in SQL a "TIME" is only "HH:MM:SS", and a date is "YYYY/MM/DD HH:MM:SS"
	private Date horarioSaida;

	private LocalDateTime dataCriacao;
}
