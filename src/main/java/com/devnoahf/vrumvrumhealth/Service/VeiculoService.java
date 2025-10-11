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
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);
        Veiculo veiculoSaved = veiculoRepository.save(veiculo);
        return veiculoMapper.toDTO(veiculoSaved);
    }

    public VeiculoDTO atualizar(Long id, VeiculoDTO veiculoDTO) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if(veiculoOptional.isPresent()) {
            Veiculo veiculoUpdated = veiculoMapper.toEntity(veiculoDTO);
            veiculoUpdated.setPlaca(veiculoDTO.getPlaca());
            veiculoUpdated.setModelo(veiculoDTO.getModelo());
            veiculoUpdated.setCapacidade(veiculoDTO.getCapacidade());
            return veiculoMapper.toDTO(veiculoRepository.save(veiculoUpdated));
        }
        return  null;

    }



    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }
}
