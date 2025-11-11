package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiarioBordoRepository extends JpaRepository<DiarioBordo, Long> {

    List<DiarioBordo> findByMotoristaEmail(String email);


}
