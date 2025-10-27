package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.TransporteDTO;
import com.devnoahf.vrumvrumhealth.Exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.Exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.Mapper.TransporteMapper;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Transporte;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import com.devnoahf.vrumvrumhealth.Repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.Repository.TransporteRepository;
import com.devnoahf.vrumvrumhealth.Repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransporteService {

    private final TransporteRepository transporteRepository;
    private final VeiculoRepository veiculoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final TransporteMapper transporteMapper;

    // 🔹 Criar transporte
    @Transactional
    public TransporteDTO criarTransporte(TransporteDTO dto) {
        validarTransporte(dto);

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID " + dto.getVeiculoId()));

        Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + dto.getAgendamentoId()));

        Transporte transporte = transporteMapper.toEntity(dto, veiculo, agendamento);
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

        validarTransporte(dto);

        transporte.setHorarioSaida(dto.getHorarioSaida());

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID " + dto.getVeiculoId()));
            transporte.setVeiculo(veiculo);
        }

        if (dto.getAgendamentoId() != null) {
            Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID " + dto.getAgendamentoId()));
            transporte.setAgendamento(agendamento);
        }

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
    private void validarTransporte(TransporteDTO dto) {
        if (dto.getHorarioSaida() == null) {
            throw new BadRequestException("O horário de saída é obrigatório.");
        }

        if (dto.getVeiculoId() == null) {
            throw new BadRequestException("O veículo é obrigatório.");
        }

        if (dto.getAgendamentoId() == null) {
            throw new BadRequestException("O agendamento é obrigatório.");
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

        // Se for paciente, só pode ver o transporte dele
        if (isPaciente) {
            String email = auth.getName();
            String emailPacienteDoTransporte = transporte.getAgendamento()
                    .getPaciente()
                    .getEmail();

            if (!email.equals(emailPacienteDoTransporte)) {
                throw new ResourceNotFoundException("Você não tem permissão para acessar este transporte.");
            }
            return transporteMapper.toDTO(transporte);
        }

        throw new ResourceNotFoundException("Usuário não autorizado.");
    }

    public TransporteDTO buscarTransportePorPaciente(String emailPaciente) {
        Transporte transporte = transporteRepository.findByAgendamentoPacienteEmail(emailPaciente)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum transporte encontrado para o paciente logado."));
        return transporteMapper.toDTO(transporte);
    }

}
