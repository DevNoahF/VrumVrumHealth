package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Model.Adm;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdmService {

    @Autowired
    private AdmRepository admRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o PasswordEncoder

    // método para cadastrar admin com senha criptografada
    public Adm cadastrarAdm(Adm adm) {
        String senhaCriptografada = passwordEncoder.encode(adm.getSenhaHash());
        adm.setSenhaHash(senhaCriptografada);
        return admRepository.save(adm);
    }

}
