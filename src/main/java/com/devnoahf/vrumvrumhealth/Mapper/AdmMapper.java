package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Model.Adm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdmMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    public AdmMapper() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Converte DTO para entidade, criptografando a senha
    public Adm toEntity(AdmDTO admDTO) {
        if (admDTO.getSenhaHash() == null || admDTO.getSenhaHash().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        Adm adm = new Adm();
        adm.setId(admDTO.getId());
        adm.setNome(admDTO.getNome());
        adm.setEmail(admDTO.getEmail());
        adm.setRole(admDTO.getRole());
        adm.setMatricula(admDTO.getMatricula());
        String senhaCriptografada = passwordEncoder.encode(admDTO.getSenhaHash());
        adm.setSenhaHash(senhaCriptografada);

        adm.setData_criacao(LocalDate.now());
        adm.setData_atualizacao(LocalDate.now());

        return adm;
    }

    // Converte entidade para DTO (não retorna senha)
    public AdmDTO toDTO(Adm adm) {
        AdmDTO dto = new AdmDTO();
        dto.setId(adm.getId());
        dto.setNome(adm.getNome());
        dto.setEmail(adm.getEmail());
        return dto;
    }

}
