package com.devnoahf.vrumvrumhealth.repository;

import com.devnoahf.vrumvrumhealth.model.DiarioBordo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiarioBordoRepository extends JpaRepository<DiarioBordo, Long> {

    List<DiarioBordo> findByMotoristaEmail(String email);


}
