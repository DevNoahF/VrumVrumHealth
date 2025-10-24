package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Mapper.AdmMapper;
import com.devnoahf.vrumvrumhealth.Entity.Adm;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdmService {
    private final AdmRepository admRepository;
    private final PasswordEncoder passwordEncoder;


    //método para cadastrar admin com senha criptografada
    public AdmDTO cadastrarAdm(AdmDTO admDTO) {
        Adm adm = new Adm();
        adm.setNome(admDTO.getNome());
        adm.setEmail(admDTO.getEmail());
        adm.setMatricula(admDTO.getMatricula());
        String senhaCriptografada = passwordEncoder.encode(admDTO.getSenha());
        adm.setSenha(senhaCriptografada);
        Adm salvo = admRepository.save(adm);
        return new AdmMapper().toDTO(salvo);


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
        if (admOptional.isPresent()) {
            Adm admExistente = admOptional.get();
            admExistente.setNome(admDTO.getNome());
            admExistente.setEmail(admDTO.getEmail());

            Adm admAtualizado = admRepository.save(admExistente);
            return new AdmMapper().toDTO(admAtualizado);
        }
        return null;
    }

    public AdmDTO buscarPorId(Long id){
        Optional<Adm> admOptional = admRepository.findById(id);
        return admOptional.map(new AdmMapper()::toDTO).orElse(null);
    }



}
