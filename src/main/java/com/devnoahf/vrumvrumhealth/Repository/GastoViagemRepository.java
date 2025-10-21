package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.GastoViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoViagemRepository extends JpaRepository<GastoViagem, Long> {
}
