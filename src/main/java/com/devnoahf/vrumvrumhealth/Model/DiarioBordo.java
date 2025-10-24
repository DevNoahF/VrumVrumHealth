package com.devnoahf.vrumvrumhealth.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_diario_bordo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiarioBordo {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal quilometragemInicial;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal quilometragemFinal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
