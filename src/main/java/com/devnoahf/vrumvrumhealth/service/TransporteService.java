package com.devnoahf.vrumvrumhealth.service;

import com.devnoahf.vrumvrumhealth.dto.TransporteDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.TransporteMapper;
import com.devnoahf.vrumvrumhealth.model.Agendamento;
import com.devnoahf.vrumvrumhealth.model.Motorista;
import com.devnoahf.vrumvrumhealth.model.Paciente;
import com.devnoahf.vrumvrumhealth.model.Transporte;
import com.devnoahf.vrumvrumhealth.model.Veiculo;
import com.devnoahf.vrumvrumhealth.repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.repository.MotoristaRepository;
import com.devnoahf.vrumvrumhealth.repository.PacienteRepository;
import com.devnoahf.vrumvrumhealth.repository.TransporteRepository;
import com.devnoahf.vrumvrumhealth.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransporteService {

    private static final long MINUTOS_ANTECEDENCIA_PADRAO = 30;

    private final TransporteRepository transporteRepository;
    private final VeiculoRepository veiculoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final MotoristaRepository motoristaRepository;
    private final PacienteRepository pacienteRepository;
    private final TransporteMapper transporteMapper;

    // 🔹 Criar transporte
    @Transactional
    public TransporteDTO criarTransporte(TransporteDTO dto) {
        validarTransporte(dto, false, null);

        Veiculo veiculo = obterVeiculo(dto.getVeiculoId());
        Agendamento agendamento = obterAgendamento(dto.getAgendamentoId());
        Motorista motorista = obterMotorista(dto.getMotoristaId());
        Paciente paciente = obterPaciente(dto.getPacienteId(), agendamento);

        LocalTime horarioSaida = calcularHorarioSaida(dto.getHorarioSaida(), agendamento, null);

        Transporte transporte = transporteMapper.toEntity(dto, veiculo, agendamento, motorista, paciente);
        transporte.setHorarioSaida(horarioSaida);

        Transporte salvo = transporteRepository.save(transporte);
        return transporteMapper.toDTO(salvo);
    }

    // 🔹 Listar todos os transportes
    public List<TransporteDTO> listarTransportes() {
        List<Transporte> transportes = transporteRepository.findAll();
        if (transportes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum transporte encontrado.");
        }
        return transportes.stream()
                .map(transporteMapper::toDTO)
                .toList();
    }

    // 🔹 Buscar por ID
    public TransporteDTO buscarPorId(Long id) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte não encontrado com ID " + id));
        return transporteMapper.toDTO(transporte);
    }

    // 🔹 Atualizar transporte
    @Transactional
    public TransporteDTO atualizarTransporte(Long id, TransporteDTO dto) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte não encontrado com ID " + id));

        validarTransporte(dto, true, id);

        Veiculo veiculo = dto.getVeiculoId() != null ? obterVeiculo(dto.getVeiculoId()) : transporte.getVeiculo();
        Agendamento agendamento = dto.getAgendamentoId() != null ?
                obterAgendamento(dto.getAgendamentoId()) : transporte.getAgendamento();
        Motorista motorista = dto.getMotoristaId() != null ?
                obterMotorista(dto.getMotoristaId()) : transporte.getMotorista();
        Paciente paciente = resolverPacienteParaAtualizacao(dto, agendamento, transporte);

        LocalTime horarioSaida = calcularHorarioSaida(dto.getHorarioSaida(), agendamento, transporte.getHorarioSaida());

        transporte.setHorarioSaida(horarioSaida);
        transporte.setVeiculo(veiculo);
        transporte.setAgendamento(agendamento);
        transporte.setMotorista(motorista);
        transporte.setPaciente(paciente);

        Transporte atualizado = transporteRepository.save(transporte);
        return transporteMapper.toDTO(atualizado);
    }

    // 🔹 Deletar transporte
    @Transactional
    public void deletarTransporte(Long id) {
        if (!transporteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transporte não encontrado com ID " + id);
        }
        transporteRepository.deleteById(id);
    }

    // 🔹 Validação de campos obrigatórios
    private void validarTransporte(TransporteDTO dto, boolean isAtualizacao, Long transporteId) {
        if (!isAtualizacao) {
            if (dto.getVeiculoId() == null) {
                throw new BadRequestException("O veículo é obrigatório.");
            }
            if (dto.getAgendamentoId() == null) {
                throw new BadRequestException("O agendamento é obrigatório.");
            }
            if (dto.getMotoristaId() == null) {
                throw new BadRequestException("O motorista é obrigatório.");
            }
        }

        if (dto.getAgendamentoId() != null) {
            transporteRepository.findByAgendamentoId(dto.getAgendamentoId()).ifPresent(transporteExistente -> {
                if (transporteId == null || !transporteExistente.getId().equals(transporteId)) {
                    throw new BadRequestException("Este agendamento já possui um transporte vinculado.");
                }
            });
        }
    }

    public TransporteDTO buscarPorIdAutenticado(Long id, Authentication auth) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte não encontrado com ID " + id));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));
        boolean isPaciente = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"));

        if (isAdmin || isMotorista) {
            return transporteMapper.toDTO(transporte);
        }

        if (isPaciente) {
            String email = auth.getName();
            String emailPacienteDoTransporte = transporte.getPaciente() != null ? transporte.getPaciente().getEmail() : null;

            if (emailPacienteDoTransporte == null || !email.equals(emailPacienteDoTransporte)) {
                throw new ResourceNotFoundException("Você não tem permissão para acessar este transporte.");
            }
            return transporteMapper.toDTO(transporte);
        }

        throw new ResourceNotFoundException("Usuário não autorizado.");
    }

    public TransporteDTO buscarTransportePorPaciente(String emailPaciente) {
        Paciente paciente = pacienteRepository.findByEmail(emailPaciente)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado para o email informado."));

        Transporte transporte = transporteRepository.findFirstByPacienteIdOrderByCreatedAtDesc(paciente.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum transporte encontrado para o paciente logado."));
        return transporteMapper.toDTO(transporte);
    }

    private Veiculo obterVeiculo(Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID " + veiculoId));
    }

    private Agendamento obterAgendamento(Long agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + agendamentoId));
    }

    private Motorista obterMotorista(Long motoristaId) {
        return motoristaRepository.findById(motoristaId)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado com ID " + motoristaId));
    }

    private Paciente obterPaciente(Long pacienteId, Agendamento agendamento) {
        if (pacienteId != null) {
            return pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID " + pacienteId));
        }

        if (agendamento.getPaciente() != null) {
            return agendamento.getPaciente();
        }

        throw new BadRequestException("O agendamento informado não possui paciente vinculado.");
    }

    private Paciente resolverPacienteParaAtualizacao(TransporteDTO dto, Agendamento agendamento, Transporte transporte) {
        if (dto.getPacienteId() != null) {
            return pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID " + dto.getPacienteId()));
        }
        if (agendamento.getPaciente() != null) {
            return agendamento.getPaciente();
        }
        if (transporte.getPaciente() != null) {
            return transporte.getPaciente();
        }
        throw new BadRequestException("O transporte precisa estar vinculado a um paciente.");
    }

    private LocalTime calcularHorarioSaida(LocalTime horarioSolicitado, Agendamento agendamento, LocalTime fallback) {
        if (horarioSolicitado != null) return horarioSolicitado;
        if (agendamento.getHoraConsulta() != null) return agendamento.getHoraConsulta().minusMinutes(MINUTOS_ANTECEDENCIA_PADRAO);
        if (fallback != null) return fallback;
        throw new BadRequestException("Não foi possível determinar o horário de saída. Informe o horário ou a hora da consulta.");
    }

    private boolean hasText(String value) { return value != null && !value.isBlank(); }
}
