package com.devnoahf.vrumvrumhealth.repository;

import com.devnoahf.vrumvrumhealth.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);
    boolean existsByEmail(String email);
}
