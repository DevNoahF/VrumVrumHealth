package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    public PacienteMapper() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Converte DTO em entidade, criptografando a senha
    public Paciente toEntity(PacienteDTO dto) {
        if (dto.getSenhaHash() == null || dto.getSenhaHash().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        // o id nao esta aqui pq é gerado automaticamente
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone(dto.getTelefone());
        paciente.setCep(dto.getCep());
        paciente.setRua(dto.getRua());
        paciente.setBairro(dto.getBairro());
        paciente.setNumero(dto.getNumero());
        paciente.setTipoAtendimentoEnum(dto.getTipoAtendimentoEnum());
        paciente.setFrequenciaEnum(dto.getFrequenciaEnum());
        paciente.setRoleEnum(dto.getRoleEnum());

        // Criptografa a senha
        paciente.setSenha(passwordEncoder.encode(dto.getSenhaHash()));


        return paciente;
    }

    // Converte entidade em DTO (não expor senha)
    public PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();

        // adicionado tudo o que vai ser retornado da entidade(menos a senha e o cpf)
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setEmail(paciente.getEmail());
        dto.setTelefone(paciente.getTelefone());
        dto.setCep(paciente.getCep());
        dto.setRua(paciente.getRua());
        dto.setBairro(paciente.getBairro());
        dto.setNumero(paciente.getNumero());
        dto.setTipoAtendimentoEnum(paciente.getTipoAtendimentoEnum());
        dto.setFrequenciaEnum(paciente.getFrequenciaEnum());
        dto.setRoleEnum(paciente.getRoleEnum());
        return dto;
    }
}
