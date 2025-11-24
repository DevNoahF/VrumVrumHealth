package com.devnoahf.vrumvrumhealth.service;

import com.devnoahf.vrumvrumhealth.dto.AgendamentoDTO;
import com.devnoahf.vrumvrumhealth.enums.StatusComprovanteEnum;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.AgendamentoMapper;
import com.devnoahf.vrumvrumhealth.model.Agendamento;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.repository.MotoristaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final AgendamentoMapper  agendamentoMapper;
    private final MotoristaRepository motoristaRepository;

    //  Criar novo agendamento
    @Transactional
    public AgendamentoDTO criarAgendamento(AgendamentoDTO dto) {
        validarAgendamento(dto);

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID " + dto.getPacienteId()
                ));

        Agendamento agendamento = agendamentoMapper.response(dto, paciente);

        // Define como PENDENTE
        agendamento.setStatusComprovanteEnum(StatusComprovanteEnum.PENDENTE);

        if (agendamento.getFrequencia() == null) {
            agendamento.setFrequencia(com.devnoahf.vrumvrumhealth.enums.FrequenciaEnum.NENHUMA);
        }

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.request(salvo);
    }


    //  Listar todos os agendamentos
    public List<AgendamentoDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        if (agendamentos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum agendamento encontrado.");
        }
        return agendamentos.stream()
                .map(agendamentoMapper::request)
                .toList();
    }

    //  Buscar por ID
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + id));
        return agendamentoMapper.request(agendamento);
    }

    //  Atualizar agendamento (parcial)
    @Transactional
    public AgendamentoDTO atualizarAgendamentoPaciente(Long id, AgendamentoDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + id));

        // Atualização parcial: só altera se campo não for null
        if (dto.getDataConsulta() != null) {
            agendamento.setDataConsulta(dto.getDataConsulta());
        }
        if (dto.getHoraConsulta() != null) {
            agendamento.setHoraConsulta(dto.getHoraConsulta());
        }
        if (dto.getComprovante() != null) {
            agendamento.setComprovante(dto.getComprovante());
        }
        if (dto.getLocalAtendimentoEnum() != null) {
            agendamento.setLocalAtendimentoEnum(dto.getLocalAtendimentoEnum());
        }
        if (dto.getRetornoCasa() != null) {
            agendamento.setRetornoCasa(dto.getRetornoCasa());
        }
        if (dto.getAcompanhante() != null) {
            agendamento.setAcompanhante(dto.getAcompanhante());
        }
        if (dto.getTipoAtendimentoEnum() != null) {
            agendamento.setTipoAtendimentoEnum(dto.getTipoAtendimentoEnum());
        }
        if (dto.getTratamentoContinuo() != null) {
            agendamento.setTratamentoContinuo(dto.getTratamentoContinuo());
        }
        if (dto.getFrequencia() != null) {
            agendamento.setFrequencia(dto.getFrequencia());
        }
        if (dto.getStatusComprovanteEnum() != null) {
            agendamento.setStatusComprovanteEnum(dto.getStatusComprovanteEnum());
        }
        if (dto.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID " + dto.getPacienteId()));
            agendamento.setPaciente(paciente);
        }

        Agendamento atualizado = agendamentoRepository.save(agendamento);
        return agendamentoMapper.request(atualizado);
    }

    public AgendamentoDTO atualizarStatusComprovante(Long id, StatusComprovanteEnum novoStatus){
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + id));

        agendamento.setStatusComprovanteEnum(novoStatus);
        Agendamento atualizado = agendamentoRepository.save(agendamento);
        return agendamentoMapper.request(atualizado);
    }

    @Transactional
    public AgendamentoDTO atribuirMotorista(Long agendamentoId, Long motoristaId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + agendamentoId));

        Motorista motorista = motoristaRepository.findById(motoristaId)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado com ID " + motoristaId));

        agendamento.setMotorista(motorista);
        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.request(salvo);
    }

    //  Deletar agendamento
    @Transactional
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento não encontrado com ID " + id);
        }

        agendamentoRepository.deleteById(id);
    }

    //  Validação de dados obrigatórios
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
                .map(agendamentoMapper::request)
                .toList();
    }

    public String buscarEmailPorIdPaciente(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .map(Paciente::getEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID " + pacienteId
                ));
    }

    public String alterarStatusComprovante(Long agendamentoId, StatusComprovanteEnum novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Agendamento não encontrado com ID " + agendamentoId
                ));
        agendamento.setStatusComprovanteEnum(novoStatus);
        agendamentoRepository.save(agendamento);
        return "Status do comprovante atualizado para " + novoStatus;

    }

}
