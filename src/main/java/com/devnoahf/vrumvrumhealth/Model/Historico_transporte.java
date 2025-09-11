package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.Estado_transporte;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_transporte")
public class Historico_transporte {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	private Long pacienteId;
	private Long agendamentoId;
	private LocalDate dataTransporte;
	private Estado_transporte estadoTransporte;
	private String observacoes;
	private LocalDateTime dataCriacao;
}
