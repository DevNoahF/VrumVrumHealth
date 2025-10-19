package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Repository.DiarioBordoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiarioBordoService {
    private final DiarioBordoRepository diarioBordoRepository;

    public List<DiarioBordo> listAll(){
        return diarioBordoRepository.findAll();
    }

    public DiarioBordo listById(Long id){
        Optional<DiarioBordo> diarioBordo = diarioBordoRepository.findById(id);
        return diarioBordo.orElse(null);
    }

    public DiarioBordo save(DiarioBordoDTO diarioBordo){
        DiarioBordo novoDiarioBordo = new DiarioBordo();
        novoDiarioBordo.setQuilometragemInicial(diarioBordo.getQuilometragemInicial());
        novoDiarioBordo.setQuilometragemFinal(diarioBordo.getQuilometragemFinal());
        novoDiarioBordo.setMotorista(diarioBordo.getMotorista());
        novoDiarioBordo.setObservacoes(diarioBordo.getObservacoes());
        return diarioBordoRepository.save(novoDiarioBordo);
    }

    public void delete(Long id){
        diarioBordoRepository.deleteById(id);
    }

    public DiarioBordo update(Long id, DiarioBordoDTO diarioBordo){
     Optional<DiarioBordo> optionalDiario = diarioBordoRepository.findById(id);
     if(optionalDiario.isPresent()){
         DiarioBordo diarioBordoUpdate = optionalDiario.get();
         diarioBordoUpdate.setQuilometragemInicial(diarioBordo.getQuilometragemInicial());
         diarioBordoUpdate.setQuilometragemFinal(diarioBordo.getQuilometragemFinal());
         diarioBordoUpdate.setMotorista(diarioBordo.getMotorista());
         diarioBordoUpdate.setObservacoes(diarioBordo.getObservacoes());
         return diarioBordoRepository.save(diarioBordoUpdate);
     }
        return null;
    }


}
