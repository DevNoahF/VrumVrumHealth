package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Service.MotoristaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("motorista")
@RequiredArgsConstructor

public class MotoristaController {

    private final MotoristaService service;


    @DeleteMapping
    public ResponseEntity<?> delete(Long id){
        if (id != null) {
            service.delete(id);
        }
        return ResponseEntity.ofNullable("Motorista com ID:  " + id + " deletado com sucesso");
    }

    public ResponseEntity<List<Motorista>> listAll(){
        return ResponseEntity.ok(service.listAll());
    }



}
