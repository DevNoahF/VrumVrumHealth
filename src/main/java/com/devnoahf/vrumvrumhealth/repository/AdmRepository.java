package com.devnoahf.vrumvrumhealth.repository;

import com.devnoahf.vrumvrumhealth.model.Adm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmRepository  extends JpaRepository<Adm,Long> {
  Optional<Adm> findByEmail(String email);
  boolean existsByEmail(String email);

}
