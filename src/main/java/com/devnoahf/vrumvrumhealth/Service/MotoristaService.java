package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository repository;

    public Motorista save(Motorista motorista){
        return repository.save(motorista);
    }

    public List<Motorista> listAll(){
        return repository.findAll();
    }

    public Motorista listById(Long id){
        Optional <Motorista> optionalMotorista = repository.findById(id);
        return optionalMotorista.orElse(null);
    }

    public void delete(Long id){
        Optional<Motorista> optionalMotorista = repository.findById(id);
        optionalMotorista.ifPresent(repository::delete);
    }

    public Motorista update(Long id, Motorista motorista){

        Optional<Motorista> optionalMotorista = repository.findById(id);
        if (optionalMotorista.isPresent()){
            Motorista motoristaUpdate = optionalMotorista.get();
            motoristaUpdate.setNome(motorista.getNome());
            motoristaUpdate.setCpf(motorista.getCpf());
            motoristaUpdate.setEmail(motorista.getEmail());
            motoristaUpdate.setTelefone(motorista.getTelefone());
            return repository.save(motoristaUpdate);
        }
        return null;
    }

}
