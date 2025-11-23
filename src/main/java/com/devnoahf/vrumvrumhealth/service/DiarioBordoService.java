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
        // ADMIN can view any; MOTORISTA can view only their own
        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));

        if (isMotorista && !diario.getMotorista().getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você não tem permissão para ver este diário.");
        }

        return diarioBordoMapper.toDTO(diario);
    }

    //🔹  salvar
    @Transactional
    public DiarioBordoDTO salvar(DiarioBordoDTO dto, Authentication auth) {
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

        // Para criação inicial, apenas salvar motorista e transporte (não exigir quilometragens)
        DiarioBordo entity = new DiarioBordo();
        entity.setMotorista(dto.getMotorista());
        entity.setTransporte(dto.getTransporte());
        // veículo e observações são opcionais neste momento
        if (dto.getVeiculo() != null) {
            entity.setVeiculo(dto.getVeiculo());
        }
        entity.setObservacoes(dto.getObservacoes());

        DiarioBordo salvo = diarioBordoRepository.save(entity);
        return diarioBordoMapper.toDTO(salvo);
    }

    // 🔹 Adicionar/atualizarAgendamentoPaciente apenas a quilometragem final (PUT específico)
    @Transactional
    public DiarioBordoDTO adicionarQuilometragemFinal(Long id, DiarioBordoDTO dto, Authentication auth) {
        DiarioBordo existente = diarioBordoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diário de bordo com ID " + id + " não encontrado."));

        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));

        if (isMotorista && !existente.getMotorista().getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizarAgendamentoPaciente seus próprios diários.");
        }

        if (dto.getQuilometragemFinal() == null) {
            throw new BadRequestException("A quilometragem final é obrigatória.");
        }

        if (existente.getQuilometragemInicial() != null && dto.getQuilometragemFinal().compareTo(existente.getQuilometragemInicial()) < 0) {
            throw new BadRequestException("A quilometragem final não pode ser menor que a inicial.");
        }

        existente.setQuilometragemFinal(dto.getQuilometragemFinal());

        DiarioBordo atualizado = diarioBordoRepository.save(existente);
        return diarioBordoMapper.toDTO(atualizado);
    }


    // 🔹 Atualizar diário existente
    @Transactional
    public DiarioBordoDTO update(Long id, DiarioBordoDTO dto, Authentication auth) {
        DiarioBordo existente = diarioBordoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diário de bordo com ID " + id + " não encontrado."));

        boolean isMotorista = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MOTORISTA"));

        // Se for motorista, garantir que ele só atualize o próprio diário
        if (isMotorista && !existente.getMotorista().getEmail().equals(auth.getName())) {
            throw new BadRequestException("Você só pode atualizarAgendamentoPaciente seus próprios diários.");
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
