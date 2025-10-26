package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.TransporteDTO;
import com.devnoahf.vrumvrumhealth.Mapper.TransporteMapper;
import com.devnoahf.vrumvrumhealth.Model.Agendamento;
import com.devnoahf.vrumvrumhealth.Model.Transporte;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import com.devnoahf.vrumvrumhealth.Repository.AgendamentoRepository;
import com.devnoahf.vrumvrumhealth.Repository.TransporteRepository;
import com.devnoahf.vrumvrumhealth.Repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransporteService {

    private final TransporteRepository transporteRepository;
    private final VeiculoRepository veiculoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final TransporteMapper transporteMapper;

    @Autowired
    public TransporteService(
            TransporteRepository transporteRepository,
            VeiculoRepository veiculoRepository,
            AgendamentoRepository agendamentoRepository,
            TransporteMapper transporteMapper
    ) {
        this.transporteRepository = transporteRepository;
        this.veiculoRepository = veiculoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.transporteMapper = transporteMapper;
    }

    // Criar transporte
    public TransporteDTO criarTransporte(TransporteDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        Transporte transporte = transporteMapper.toEntity(dto, veiculo, agendamento);
        Transporte salvo = transporteRepository.save(transporte);

        return transporteMapper.toDTO(salvo);
    }

    // Listar todos
    public List<TransporteDTO> listarTransportes() {
        return transporteRepository.findAll()
                .stream()
                .map(transporteMapper::toDTO)
                .toList();
    }

    // Buscar por ID
    public TransporteDTO buscarPorId(Long id) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transporte não encontrado"));
        return transporteMapper.toDTO(transporte);
    }

    // Atualizar transporte
    public TransporteDTO atualizarTransporte(Long id, TransporteDTO dto) {
        Optional<Transporte> transporteOpt = transporteRepository.findById(id);

        if (transporteOpt.isEmpty()) {
            throw new EntityNotFoundException("Transporte não encontrado");
        }

        Transporte transporte = transporteOpt.get();

        transporte.setHorarioSaida(dto.getHorarioSaida());

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
            transporte.setVeiculo(veiculo);
        }

        if (dto.getAgendamentoId() != null) {
            Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                    .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
            transporte.setAgendamento(agendamento);
        }

        Transporte atualizado = transporteRepository.save(transporte);
        return transporteMapper.toDTO(atualizado);
    }

    // Deletar transporte
    public void deletarTransporte(Long id) {
        if (!transporteRepository.existsById(id)) {
            throw new EntityNotFoundException("Transporte não encontrado");
        }
        transporteRepository.deleteById(id);
    }
}
