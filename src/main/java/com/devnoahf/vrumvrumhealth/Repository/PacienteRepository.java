package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.Paciente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    static boolean existsByEmail(@Email(message = "Email inválido!") @NotBlank(message = "Email é obrigatorio!") String email) {
        return false;
    }

    Optional<Paciente> findByEmail(String email);
}
