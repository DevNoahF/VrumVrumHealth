package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.GastoViagemDTO;
import com.devnoahf.vrumvrumhealth.Mapper.GastoViagemMapper;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Model.GastoViagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devnoahf.vrumvrumhealth.Repository.DiarioBordoRepository;
import com.devnoahf.vrumvrumhealth.Repository.GastoViagemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GastoViagemService {

    @Autowired
    private GastoViagemRepository gastoViagemRepository;

    @Autowired
    private DiarioBordoRepository diarioBordoRepository;

    @Autowired
    private GastoViagemMapper gastoViagemMapper;

    public GastoViagemDTO cadastrarGastoViagem(GastoViagemDTO dto) {
        DiarioBordo diario = null;

        if (dto.getDiarioBordoId() != null) {
            diario = diarioBordoRepository.findById(dto.getDiarioBordoId()).orElse(null);
        }

        GastoViagem gasto = gastoViagemMapper.toEntity(dto, diario);
        GastoViagem salvo = gastoViagemRepository.save(gasto);

        return gastoViagemMapper.toDTO(salvo);
    }



    public List<GastoViagemDTO> listarGastos() {
        return gastoViagemRepository.findAll()
                .stream()
                .map(gastoViagemMapper::toDTO)
                .toList();
    }

    public void deletarGasto(Long id) {
        if (gastoViagemRepository.existsById(id)) {
            gastoViagemRepository.deleteById(id);
        }
    }

    public GastoViagemDTO buscarPorId(Long id) {
        Optional<GastoViagem> gasto = gastoViagemRepository.findById(id);
        return gasto.map(gastoViagemMapper::toDTO).orElse(null);
    }

    public GastoViagemDTO atualizarGasto(Long id, GastoViagemDTO dto) {
        Optional<GastoViagem> gastoExistenteOpt = gastoViagemRepository.findById(id);

        if (gastoExistenteOpt.isEmpty()) {
            return null;
        }

        GastoViagem gastoExistente = gastoExistenteOpt.get();

        if (dto.getDescricao() != null) {
            gastoExistente.setDescricao(dto.getDescricao());
        }

        if (dto.getValor() != null) {
            gastoExistente.setValor(dto.getValor());
        }

        if (dto.getDiarioBordoId() != null) {
            DiarioBordo diario = diarioBordoRepository.findById(dto.getDiarioBordoId()).orElse(null);
            gastoExistente.setDiarioBordo(diario);
        }

        GastoViagem atualizado = gastoViagemRepository.save(gastoExistente);
        return gastoViagemMapper.toDTO(atualizado);
    }

}
