package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.VeiculoDTO;
import com.devnoahf.vrumvrumhealth.Mapper.VeiculoMapper;
import com.devnoahf.vrumvrumhealth.Model.Veiculo;
import com.devnoahf.vrumvrumhealth.Repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoMapper veiculoMapper;

    public List<VeiculoDTO> listarTodos() {
        List<Veiculo> veiculos = veiculoRepository.findAll();
        return veiculos.stream()
                .map(veiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VeiculoDTO buscarPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
        return veiculoMapper.toDTO(veiculo);
    }

    public VeiculoDTO salvar(VeiculoDTO veiculoDTO) {
        try {
            Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);
            Veiculo salvo = veiculoRepository.save(veiculo);
            return veiculoMapper.toDTO(salvo);

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa já cadastrada");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar veículo");
        }
    }

    public VeiculoDTO atualizar(Long id, VeiculoDTO veiculoDTO) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));

        try {
            existente.setPlaca(veiculoDTO.getPlaca());
            existente.setModelo(veiculoDTO.getModelo());
            existente.setCapacidade(veiculoDTO.getCapacidade());

            Veiculo atualizado = veiculoRepository.save(existente);
            return veiculoMapper.toDTO(atualizado);

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa já cadastrada");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar veículo");
        }
    }

    public void deletar(Long id) {
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(id);

        if (veiculoOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }

        try {
            veiculoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar veículo");
        }
    }
}
