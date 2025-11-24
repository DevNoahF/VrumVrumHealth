package com.devnoahf.vrumvrumhealth.repository;

import com.devnoahf.vrumvrumhealth.model.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
    Optional<Transporte> findByAgendamentoPacienteEmail(String email);
    List<Transporte> findByMotoristaId(Long motoristaId);
    List<Transporte> findByPacienteId(Long pacienteId);
    Optional<Transporte> findByAgendamentoId(Long agendamentoId);
    Optional<Transporte> findFirstByPacienteIdOrderByCreatedAtDesc(Long pacienteId);
}
