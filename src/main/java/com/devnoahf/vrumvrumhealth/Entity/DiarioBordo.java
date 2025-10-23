package com.devnoahf.vrumvrumhealth.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Entity
@Table(name = " diario_bordo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiarioBordo {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    @Column(precision = 10, scale = 2)
    private BigDecimal quilometragemInicial;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    @Column(precision = 10, scale = 2)
    private BigDecimal quilometragemFinal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    @Column(nullable = false)
    private Motorista motorista;


    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    @Column(nullable = false)
    private Veiculo veiculos;

    @ManyToOne
    @JoinColumn(name = "transporte_id")
    @Column(nullable = false)
    private Transporte transporte;


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
