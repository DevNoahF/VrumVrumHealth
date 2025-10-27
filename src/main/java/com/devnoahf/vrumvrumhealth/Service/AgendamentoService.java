package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Mapper.AgendamentoMapper;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    // 🔹 Criar novo agendamento
    @Transactional
    public AgendamentoDTO criarAgendamento(AgendamentoDTO dto) {
        validarAgendamento(dto);

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID " + dto.getPacienteId()));

        Agendamento agendamento = agendamentoMapper.toEntity(dto, paciente);
        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDTO(salvo);
    }

    // 🔹 Listar todos os agendamentos
    public List<AgendamentoDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        if (agendamentos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum agendamento encontrado.");
        }
        return agendamentos.stream()
                .map(agendamentoMapper::toDTO)
                .toList();
    }

    // 🔹 Buscar por ID
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + id));
        return agendamentoMapper.toDTO(agendamento);
    }

    // 🔹 Atualizar agendamento
    @Transactional
    public AgendamentoDTO atualizarAgendamento(Long id, AgendamentoDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + id));

        validarAgendamento(dto);

        agendamento.setDataConsulta(dto.getDataConsulta());
        agendamento.setHoraConsulta(dto.getHoraConsulta());
        agendamento.setComprovante(dto.getComprovante());
        agendamento.setLocal_atendimento(dto.getLocalAtendimentoEnum());
        agendamento.setStatusEnum(dto.getStatusEnum());
        agendamento.setRetorno_casa(dto.getRetornoCasa());

        if (dto.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID " + dto.getPacienteId()));
            agendamento.setPaciente(paciente);
        }

        Agendamento atualizado = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDTO(atualizado);
    }

    // 🔹 Deletar agendamento
    @Transactional
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento não encontrado com ID " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    // 🔹 Validação de dados obrigatórios
    private void validarAgendamento(AgendamentoDTO dto) {
        if (dto.getDataConsulta() == null) {
            throw new BadRequestException("A data da consulta é obrigatória.");
        }
        if (dto.getHoraConsulta() == null) {
            throw new BadRequestException("A hora da consulta é obrigatória.");
        }
        if (dto.getLocalAtendimentoEnum() == null) {
            throw new BadRequestException("O local de atendimento é obrigatório.");
        }
        if (dto.getPacienteId() == null) {
            throw new BadRequestException("O paciente é obrigatório.");
        }
    }

    public List<AgendamentoDTO> listarAgendamentosPorPaciente(String emailPaciente) {
        List<Agendamento> agendamentos = agendamentoRepository.findByPacienteEmail(emailPaciente);
        return agendamentos.stream()
                .map(agendamentoMapper::toDTO)
                .toList();
    }

    public String buscarEmailPorIdPaciente(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .map(p -> p.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID " + pacienteId
                ));
    }


}
