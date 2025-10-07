package com.devnoahf.vrumvrumhealth.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tb_transporte")
@Data
public class Transporte {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horarioSaida;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	private LocalDateTime dataAtualizacao;

	@ManyToOne
	@JoinColumn(name = "veiculo_id",nullable = false)
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "agendamento_id",nullable = false)
	private Agendamento agendamento;

	@PrePersist
	protected void onCreate() {
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now(); // opcional
	}

	@PreUpdate
	protected void onUpdate() {
		this.dataAtualizacao = LocalDateTime.now();
	}



}
