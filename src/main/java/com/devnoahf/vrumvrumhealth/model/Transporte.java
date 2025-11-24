package com.devnoahf.vrumvrumhealth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

@Builder
@Entity
@Table(name = "transporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horarioSaida;

	@ManyToOne
	@JoinColumn(name = "veiculo_id",nullable = false)
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "agendamento_id",nullable = false)
	private Agendamento agendamento;

	@ManyToOne
	@JoinColumn(name = "motorista_id", nullable = false)
	private Motorista motorista;

	@ManyToOne
	@JoinColumn(name = "paciente_id", nullable = false)
	private Paciente paciente;
	@Column(nullable = false, updatable = false, name = "created_at")
	@CreationTimestamp
	private Instant createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Instant updatedAt;


	@PrePersist
	protected void onCreate() {
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now(); // opcional
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = Instant.now();
	}
}
