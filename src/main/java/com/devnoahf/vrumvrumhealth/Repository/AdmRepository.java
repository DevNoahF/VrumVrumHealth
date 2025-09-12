package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.Adm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmRepository  extends JpaRepository<Adm,Long> {

    public Optional<Adm> findByEmail(String email);
}
