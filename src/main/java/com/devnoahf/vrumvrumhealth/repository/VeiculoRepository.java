package com.devnoahf.vrumvrumhealth.repository;

import com.devnoahf.vrumvrumhealth.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
