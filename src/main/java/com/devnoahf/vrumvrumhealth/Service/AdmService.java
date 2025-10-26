package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Mapper.AdmMapper;
import com.devnoahf.vrumvrumhealth.Model.Adm;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmService {

    private final AdmRepository admRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdmMapper admMapper;

    @Autowired
    public AdmService(AdmRepository admRepository, PasswordEncoder passwordEncoder, AdmMapper admMapper) {
        this.admRepository = admRepository;
        this.passwordEncoder = passwordEncoder;
        this.admMapper = admMapper;
    }

    // Cadastrar admin com senha criptografada
    public AdmDTO cadastrarAdm(AdmDTO admDTO) {
        if (admRepository.findByEmail(admDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Já existe um administrador com esse email.");
        }

        Adm adm = admMapper.toEntity(admDTO);
        adm.setSenha(passwordEncoder.encode(admDTO.getSenha()));

        Adm salvo = admRepository.save(adm);
        return admMapper.toDTO(salvo);
    }

    public List<AdmDTO> listarAdmins() {
        return admRepository.findAll()
                .stream()
                .map(admMapper::toDTO)
                .toList();
    }

    public void deletarAdm(Long id) {
        if (!admRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administrador com ID " + id + " não encontrado.");
        }
        admRepository.deleteById(id);
    }

    public AdmDTO atualizarAdm(AdmDTO admDTO, Long id) {
        Adm admExistente = admRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador com ID " + id + " não encontrado."));

        admExistente.setNome(admDTO.getNome());
        admExistente.setEmail(admDTO.getEmail());

        if (admDTO.getSenha() != null && !admDTO.getSenha().isBlank()) {
            admExistente.setSenha(passwordEncoder.encode(admDTO.getSenha()));
        }

        Adm atualizado = admRepository.save(admExistente);
        return admMapper.toDTO(atualizado);
    }

    public AdmDTO buscarPorId(Long id) {
        Adm adm = admRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador com ID " + id + " não encontrado."));
        return admMapper.toDTO(adm);
    }

    public void mudarSenha(String email, String novaSenha) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("O email é obrigatório para alterar a senha.");
        }

        Adm adm = admRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Administrador com email " + email + " não encontrado."
                ));

        if (novaSenha == null || novaSenha.isBlank()) {
            throw new BadRequestException("A nova senha não pode estar vazia.");
        }

        adm.setSenha(passwordEncoder.encode(novaSenha));
        admRepository.save(adm);
    }

    // verificar email
    public Adm findByEmail(String email) {
        return admRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(" Email nao encontrado."));
    }
}
