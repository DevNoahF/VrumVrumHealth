package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Model.Adm;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdmService {

    @Autowired
    private AdmRepository admRepository;  // injeta o repositório de Adm

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o PasswordEncoder

    // método para cadastrar admin com senha criptografada
    public Adm cadastrarAdm(Adm adm) {
        String senhaCriptografada = passwordEncoder.encode(adm.getSenha_hash());
        adm.setSenha_hash(senhaCriptografada);
        return admRepository.save(adm);
    }
}
