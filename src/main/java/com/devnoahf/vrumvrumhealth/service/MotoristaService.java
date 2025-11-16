package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository repository;
    private final PasswordEncoder passwordEncoder;

    // 🔹 Criar motorista com senha criptografada
    public Motorista save(Motorista motorista) {
        if (repository.existsByEmail(motorista.getEmail())) {
            throw new BadRequestException("Já existe um motorista cadastrado com esse email.");
        }

        if (motorista.getSenha() == null || motorista.getSenha().isBlank()) {
            throw new BadRequestException("A senha não pode estar vazia.");
        }

        motorista.setSenha(passwordEncoder.encode(motorista.getSenha()));
        return repository.save(motorista);
    }

    // 🔹 Listar todos os motoristas
    public List<Motorista> listAll() {
        List<Motorista> motoristas = repository.findAll();
        if (motoristas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum motorista encontrado.");
        }
        return motoristas;
    }

    // 🔹 Buscar motorista por ID
    public Motorista listById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista com ID " + id + " não encontrado."));
    }

    // 🔹 Deletar motorista
    public void delete(Long id) {
        Motorista motorista = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista com ID " + id + " não encontrado."));
        repository.delete(motorista);
    }

    // 🔹 Atualizar motorista
    public Motorista update(Long id, Motorista motorista) {
        Motorista motoristaExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista com ID " + id + " não encontrado."));

        motoristaExistente.setNome(motorista.getNome());
        motoristaExistente.setCpf(motorista.getCpf());
        motoristaExistente.setEmail(motorista.getEmail());
        motoristaExistente.setTelefone(motorista.getTelefone());

        return repository.save(motoristaExistente);
    }

    // 🔹 Mudar senha de motorista
    public void mudarSenha(String email, String novaSenha) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("O email é obrigatório para alterar a senha.");
        }

        Motorista motorista = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Motorista com email " + email + " não encontrado."
                ));

        if (novaSenha == null || novaSenha.isBlank()) {
            throw new BadRequestException("A nova senha não pode estar vazia.");
        }

        motorista.setSenha(passwordEncoder.encode(novaSenha));
        repository.save(motorista);
    }

    // Buscar por email
    public Motorista findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(" Email nao encontrado."));
    }

}
