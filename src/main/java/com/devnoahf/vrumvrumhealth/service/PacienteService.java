package com.devnoahf.vrumvrumhealth.service;

import com.devnoahf.vrumvrumhealth.dto.PacienteDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.repository.AgendamentoRepository;
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
    private final AgendamentoRepository agendamentoRepository;


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
        pacienteExistente.setCep(pacienteDTO.getCep());
        pacienteExistente.setRua(pacienteDTO.getRua());
        pacienteExistente.setBairro(pacienteDTO.getBairro());
        pacienteExistente.setNumero(pacienteDTO.getNumero());
        pacienteExistente.setDdd(pacienteDTO.getDdd());
        pacienteExistente.setComplemento(pacienteDTO.getComplemento());

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
