package com.devnoahf.vrumvrumhealth.Repository;

import com.devnoahf.vrumvrumhealth.Model.Adm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmRepository  extends JpaRepository<Adm,Long> {
}
