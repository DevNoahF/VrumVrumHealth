package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.Enum.StatusEnum;
import com.devnoahf.vrumvrumhealth.Mapper.AgendamentoMapper;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;

    @Autowired
    private AgendamentoMapper agendamentoMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              PacienteRepository pacienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    // Criar novo agendamento
    public AgendamentoDTO criarAgendamento(AgendamentoDTO dto) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(dto.getPacienteId());
        if (pacienteOptional.isEmpty()) {
            return null;
        }

        Paciente paciente = pacienteOptional.get();
        Agendamento agendamento = agendamentoMapper.toEntity(dto, paciente);

        // Define como PENDENTE
        agendamento.setStatusEnum(StatusEnum.PENDENTE);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDTO(salvo);
    }

    // Listar todos
    public List<AgendamentoDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return agendamentos.stream()
                .map(agendamentoMapper::toDTO)
                .toList();
    }

    // Buscar por ID
    public AgendamentoDTO buscarPorId(Long id) {
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);
        return agendamentoOptional.map(agendamentoMapper::toDTO).orElse(null);
    }

    // Atualizar agendamento
    public AgendamentoDTO atualizarAgendamento(AgendamentoDTO dto, Long id) {
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);

        if (agendamentoOptional.isPresent()) {
            Agendamento agendamentoExistente = agendamentoOptional.get();

            agendamentoExistente.setData_consulta(dto.getDataConsulta());
            agendamentoExistente.setHora_consulta(dto.getHoraConsulta());
            agendamentoExistente.setLocal_atendimento(dto.getLocalAtendimentoEnum());
            agendamentoExistente.setStatusEnum(dto.getStatusEnum());
            agendamentoExistente.setRetorno_casa(dto.getRetornoCasa());
            agendamentoExistente.setAcompanhante(dto.getAcompanhante());
            agendamentoExistente.setImagemComprovante(dto.getImagemComprovante());

            if (dto.getPacienteId() != null) {
                Optional<Paciente> pacienteOptional = pacienteRepository.findById(dto.getPacienteId());
                pacienteOptional.ifPresent(agendamentoExistente::setPaciente);
            }

            Agendamento atualizado = agendamentoRepository.save(agendamentoExistente);
            return agendamentoMapper.toDTO(atualizado);
        } else {
            return null;
        }
    }

    // Deletar agendamento
    public void deletarAgendamento(Long id) {
        if (agendamentoRepository.existsById(id)) {
            agendamentoRepository.deleteById(id);
        }
    }

    // Atualiza apenas o status de um agendamento
    public AgendamentoDTO atualizarStatus(Long id, StatusEnum novoStatus) {
        Optional<Agendamento> optionalAgendamento = agendamentoRepository.findById(id);

        if (optionalAgendamento.isEmpty()) {
            return null;
        }

        Agendamento agendamento = optionalAgendamento.get();
        agendamento.setStatusEnum(novoStatus); // altera só o status
        Agendamento atualizado = agendamentoRepository.save(agendamento);

        return agendamentoMapper.toDTO(atualizado);
    }

}
