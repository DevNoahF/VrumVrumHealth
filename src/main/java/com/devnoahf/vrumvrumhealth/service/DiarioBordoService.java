package com.devnoahf.vrumvrumhealth.service;

import com.devnoahf.vrumvrumhealth.dto.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.exception.BadRequestException;
import com.devnoahf.vrumvrumhealth.exception.ResourceNotFoundException;
import com.devnoahf.vrumvrumhealth.mapper.DiarioBordoMapper;
import com.devnoahf.vrumvrumhealth.model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.repository.DiarioBordoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiarioBordoService {

    private final DiarioBordoRepository diarioBordoRepository;
    private final DiarioBordoMapper diarioBordoMapper;

    // 🔹 Listar todos
    public List<DiarioBordoDTO> listAll() {
        List<DiarioBordo> diarios = diarioBordoRepository.findAll();
        return diarios.stream()
                .map(diarioBordoMapper::toDTO)
                .toList();
    }

    // 🔹 Buscar por ID
    public DiarioBordoDTO listById(Long id, Authentication auth) {
        DiarioBordo diario = diarioBordoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Diário de bordo com ID " + id + " não encontrado."
                ));
        return diarioBordoMapper.toDTO(diario);
    }

    //🔹  salvar
    @Transactional
    public DiarioBordoDTO salvar(DiarioBordoDTO dto, Authentication auth) {
        validarDados(dto);

        // Apenas ADMIN ou MOTORISTA podem criar
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));

        if (!isAdmin && !isMotorista) {
            throw new BadRequestException("Apenas administradores ou motoristas podem criar um diário de bordo.");
        }

        // Se for motorista, garantir que o diário pertence a ele
        if (isMotorista && dto.getMotorista() == null) {
            throw new BadRequestException("O motorista deve estar vinculado ao diário de bordo.");
        }

        DiarioBordo entity = diarioBordoMapper.toEntity(dto);
        DiarioBordo salvo = diarioBordoRepository.save(entity);
        return diarioBordoMapper.toDTO(salvo);
    }


    // 🔹 Atualizar diário existente
    @Transactional
    public DiarioBordoDTO update(Long id, DiarioBordoDTO dto, Authentication auth) {
        DiarioBordo existente = diarioBordoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diário de bordo com ID " + id + " não encontrado."));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));

        // Se for motorista, garantir que ele só atualize o próprio diário
        if (isMotorista && !existente.getMotorista().getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizar seus próprios diários.");
        }

        validarDados(dto);
        existente.setQuilometragemInicial(dto.getQuilometragemInicial());
        existente.setQuilometragemFinal(dto.getQuilometragemFinal());
        existente.setObservacoes(dto.getObservacoes());
        existente.setVeiculo(dto.getVeiculo());
        existente.setTransporte(dto.getTransporte());

        DiarioBordo atualizado = diarioBordoRepository.save(existente);
        return diarioBordoMapper.toDTO(atualizado);
    }


    // 🔹 Deletar diário
    @Transactional
    public void delete(Long id) {
        if (!diarioBordoRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Diário de bordo com ID " + id + " não encontrado."
            );
        }
        diarioBordoRepository.deleteById(id);
    }

    // 🔹 Validação de dados antes de salvar
    private void validarDados(DiarioBordoDTO dto) {
        if (dto.getQuilometragemInicial() == null || dto.getQuilometragemFinal() == null) {
            throw new BadRequestException("As quilometragens inicial e final são obrigatórias.");
        }

        if (dto.getQuilometragemFinal().compareTo(dto.getQuilometragemInicial()) < 0) {
            throw new BadRequestException("A quilometragem final não pode ser menor que a inicial.");
        }

        if (dto.getMotorista() == null) {
            throw new BadRequestException("O motorista é obrigatório.");
        }

        if (dto.getVeiculo() == null) {
            throw new BadRequestException("O veículo é obrigatório.");
        }

        if (dto.getTransporte() == null) {
            throw new BadRequestException("O transporte é obrigatório.");
        }
    }


    // 🔹 listar por nomes de motoristas
    public List<DiarioBordo> listarPorMotorista(String nome) {
        List<DiarioBordo> diarios = diarioBordoRepository.findByMotoristaEmail(nome);

        if (diarios.isEmpty()) {
            throw new ResourceNotFoundException("Motorista não encontrado.");
        }

        return diarios;
    }



}

