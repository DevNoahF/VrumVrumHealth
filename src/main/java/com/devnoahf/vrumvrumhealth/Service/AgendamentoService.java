package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Mapper.AgendamentoMapper;
import com.devnoahf.vrumvrumhealth.Entity.Agendamento;
import com.devnoahf.vrumvrumhealth.Entity.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    //  Criar novo agendamento
    public AgendamentoDTO criarAgendamento(AgendamentoDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        Agendamento agendamento = agendamentoMapper.toEntity(dto, paciente);
        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDTO(salvo);
    }

    //  Listar todos
    public List<AgendamentoDTO> listarAgendamentos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(agendamentoMapper::toDTO)
                .toList();
    }

    // Buscar por ID
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        return agendamentoMapper.toDTO(agendamento);
    }

    // Atualizar agendamento
    public AgendamentoDTO atualizarAgendamento(Long id, AgendamentoDTO dto) {
        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);

        if (agendamentoOpt.isPresent()) {
            Agendamento agendamento = agendamentoOpt.get();

            // Atualiza campos básicos
            agendamento.setDataConsulta(dto.getDataConsulta());
            agendamento.setHoraConsulta(dto.getHoraConsulta());
            agendamento.setComprovante(dto.getComprovante());
            agendamento.setLocal_atendimento(dto.getLocalAtendimentoEnum());
            agendamento.setStatusEnum(dto.getStatusEnum());
            agendamento.setRetorno_casa(dto.getRetornoCasa());

            // Atualiza paciente se vier novo ID
            if (dto.getPacienteId() != null) {
                Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                        .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
                agendamento.setPaciente(paciente);
            }

            Agendamento atualizado = agendamentoRepository.save(agendamento);
            return agendamentoMapper.toDTO(atualizado);
        } else {
            throw new EntityNotFoundException("Agendamento não encontrado");
        }
    }

    // Deletar Agendamento
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado");
        }
        agendamentoRepository.deleteById(id);
    }
}
