package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Mapper.AdmMapper;
import com.devnoahf.vrumvrumhealth.Model.Adm;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmService {
    private final AdmRepository admRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AdmMapper admMapper;

    public AdmService(AdmRepository admRepository, PasswordEncoder passwordEncoder) {
        this.admRepository = admRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //método para cadastrar admin com senha criptografada
    public AdmDTO cadastrarAdm(AdmDTO admDTO) {
        Adm adm = new Adm();
        adm.setNome(admDTO.getNome());
        adm.setEmail(admDTO.getEmail());
        adm.setMatricula(admDTO.getMatricula());
        adm.setRoleEnum(admDTO.getRoleEnum());
        String senhaCriptografada = passwordEncoder.encode(admDTO.getSenhaHash());
        adm.setSenhaHash(senhaCriptografada);
        Adm salvo = admRepository.save(adm);
        return admMapper.toDTO(salvo);


    }

    public List<AdmDTO> listarAdmins(){
        List<Adm> admins = admRepository.findAll();
        return admins.stream()
                .map(new AdmMapper()::toDTO)
                .toList();
    }

    public void deletarAdm(Long id){
        if (admRepository.existsById(id)){
            admRepository.deleteById(id);
        }
    }

    public AdmDTO atualizarAdm(AdmDTO admDTO, Long id){
        Optional<Adm> admOptional = admRepository.findById(id);
        if (admOptional.isPresent()){
            Adm admExistente = admOptional.get();
            admExistente.setNome(admDTO.getNome());
            admExistente.setEmail(admDTO.getEmail());

            if (admDTO.getSenhaHash() != null && !admDTO.getSenhaHash().isEmpty()){
                String senhaCriptografada = passwordEncoder.encode(admDTO.getSenhaHash());
                admExistente.setSenhaHash(senhaCriptografada);
            }
            Adm atualizado = admRepository.save(admExistente);
            return new AdmMapper().toDTO(atualizado);
        } else {
            return null;
        }
    }

    public AdmDTO buscarPorId(Long id){
        Optional<Adm> admOptional = admRepository.findById(id);
        return admOptional.map(new AdmMapper()::toDTO).orElse(null);
    }

    public void mudarSenha(String email, String novaSenha){
        Optional<Adm> admOptional = Optional.ofNullable(admRepository.findByEmail(email));
        if (admOptional.isPresent()){
            Adm adm = admOptional.get();
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            adm.setSenhaHash(senhaCriptografada);
            admRepository.save(adm);
        }
    }


}
