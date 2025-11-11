package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final PacienteMapper pacienteMapper;

    // 🔹 Cadastrar paciente com senha criptografada
    public Paciente cadastrarPaciente(@Valid PacienteDTO pacienteDTO) {
        if (pacienteRepository.existsByEmail(pacienteDTO.getEmail())) {
            throw new BadRequestException("Já existe um paciente com esse e-mail.");
        }

        String senhaCriptografada = passwordEncoder.encode(pacienteDTO.getSenha());
        pacienteDTO.setSenha(senhaCriptografada);

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        return pacienteRepository.save(paciente);
    }

    // 🔹 Listar todos os pacientes
    public List<PacienteDTO> listarPaciente() {
        List<Paciente> pacientes = pacienteRepository.findAll();

        if (pacientes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum paciente encontrado.");
        }

        return pacientes.stream()
                .map(pacienteMapper::toDTO)
                .toList();
    }

    // 🔹 Deletar paciente
    public void deletarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID " + id + " não encontrado."));
        pacienteRepository.delete(paciente);
    }

    // 🔹 Atualizar paciente
    public PacienteDTO atualizarPaciente(PacienteDTO pacienteDTO, Long id) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID " + id + " não encontrado."));

        pacienteExistente.setNome(pacienteDTO.getNome());
        pacienteExistente.setEmail(pacienteDTO.getEmail());
        pacienteExistente.setTelefone(pacienteDTO.getTelefone());
        pacienteExistente.setCpf(pacienteDTO.getCpf());
        pacienteExistente.setDataNascimento(pacienteDTO.getDataNascimento());

        if (pacienteDTO.getSenha() != null && !pacienteDTO.getSenha().isBlank()) {
            pacienteExistente.setSenha(passwordEncoder.encode(pacienteDTO.getSenha()));
        }

        Paciente atualizado = pacienteRepository.save(pacienteExistente);
        return pacienteMapper.toDTO(atualizado);
    }

    // 🔹 Buscar paciente por ID
    public PacienteDTO buscarPorIdPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID " + id + " não encontrado."));
        return pacienteMapper.toDTO(paciente);
    }

    // 🔹 Mudar senha
    public void mudarSenhaPaciente(String email, String novaSenha) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com e-mail " + email + " não encontrado."));

        if (novaSenha == null || novaSenha.isBlank()) {
            throw new BadRequestException("A nova senha não pode estar vazia.");
        }

        paciente.setSenha(passwordEncoder.encode(novaSenha));
        pacienteRepository.save(paciente);
    }

    // find by email
    public Paciente findByEmail(String email) {
        return pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(" Email nao encontrado."));
    }
}
