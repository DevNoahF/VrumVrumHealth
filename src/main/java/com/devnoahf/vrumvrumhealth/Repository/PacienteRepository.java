package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByEmail(String email);
}
