package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Service.MotoristaService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("motorista")
@RequiredArgsConstructor

public class MotoristaController {

    private final MotoristaService service;

    @PostMapping
    public ResponseEntity<MotoristaDTO> save(MotoristaDTO motoristaDTO){
        Motorista newMotorista = MotoristaMapper.toEntity();
        Motorista savedMotorista = service.save(newMotorista);
        return ResponseEntity.status(HttpStatus.CREATED).body(MotoristaMapper.toDTO(savedMotorista));
    }
}
