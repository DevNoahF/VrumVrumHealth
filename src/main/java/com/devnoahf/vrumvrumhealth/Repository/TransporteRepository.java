package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
}
