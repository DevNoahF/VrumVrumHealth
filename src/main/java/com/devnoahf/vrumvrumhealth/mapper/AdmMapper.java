package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Model.Adm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdmMapper {

    private final PasswordEncoder passwordEncoder;

    public AdmMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Converte DTO para entidade, criptografando a senha
    public Adm toEntity(AdmDTO admDTO) {
        if (admDTO.getSenha() == null || admDTO.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        Adm adm = new Adm();

        // Não setar o ID aqui no cadastro (gerado automaticamente pelo banco)


        adm.setNome(admDTO.getNome());
        adm.setEmail(admDTO.getEmail());
        adm.setMatricula(admDTO.getMatricula());

        // Criptografa a senha -> refatorar depois para usar o service
        String senhaCriptografada = passwordEncoder.encode(admDTO.getSenha());
        adm.setSenha(senhaCriptografada);


        return adm;
    }

    // Converte entidade para DTO (não retorna senha)
    public AdmDTO toDTO(Adm adm) {

        // Retorna apenas os campos necessários(sem senha e matricula)
        AdmDTO dto = new AdmDTO();
        dto.setId(adm.getId());
        dto.setNome(adm.getNome());
        dto.setEmail(adm.getEmail());
        dto.setMatricula(adm.getMatricula());
        return dto;
    }

}
