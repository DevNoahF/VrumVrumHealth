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

        // Não setar o ID aqui no cadastro
        // adm.setId(admDTO.getId());

        adm.setNome(admDTO.getNome());
        adm.setEmail(admDTO.getEmail());
        adm.setRole(admDTO.getRole());
        adm.setMatricula(admDTO.getMatricula());

        // Criptografa a senha -> refatorar depois para usar o service
        String senhaCriptografada = passwordEncoder.encode(admDTO.getSenhaHash());
        adm.setSenhaHash(senhaCriptografada);


        return adm;
    }

    // Converte entidade para DTO (não retorna senha)
    public AdmDTO toDTO(Adm adm) {

        // Retorna apenas os campos necessários(sem senha e matricula)
        AdmDTO dto = new AdmDTO();
        dto.setId(adm.getId());
        dto.setNome(adm.getNome());
        dto.setEmail(adm.getEmail());
        return dto;
    }

}
