package com.devnoahf.vrumvrumhealth.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "transporte")
@Data
public class Transporte {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horarioSaida;

	@Column(nullable = false, updatable = false,name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "veiculo_id",nullable = false)
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "agendamento_id",nullable = false)
	private Agendamento agendamento;

	@OneToMany(mappedBy = "transporte",cascade = CascadeType.ALL)
	private List<DiarioBordo> diariosBordo;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now(); // opcional
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}



}
