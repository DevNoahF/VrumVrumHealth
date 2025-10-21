package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.MotoristaDTO;
import com.devnoahf.vrumvrumhealth.Mapper.MotoristaMapper;
import com.devnoahf.vrumvrumhealth.Model.Motorista;
import com.devnoahf.vrumvrumhealth.Service.MotoristaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("motorista")
@RequiredArgsConstructor

public class MotoristaController {

    private final MotoristaService service;


    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null){
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorista nao encontrado!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listAll(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById( @PathVariable Long id){
        Motorista motorista = service.listById(id);
        if (id != null){
            return ResponseEntity.ok(motorista);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorista nao encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<MotoristaDTO> save(@RequestBody MotoristaDTO dto){
        Motorista motorista = MotoristaMapper.toEntity(dto);
        Motorista motoristaSalvo = service.save(motorista);
        MotoristaDTO motoristaDTO = MotoristaMapper.toDTO(motoristaSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> update(@PathVariable Long id, @RequestBody MotoristaDTO dto){
        Motorista motoristaSalvo = MotoristaMapper.toEntity(dto);
        Motorista motoristaAtualizado = service.update(id, motoristaSalvo);
        if (motoristaAtualizado != null){
            MotoristaDTO motoristaDTO = MotoristaMapper.toDTO(motoristaAtualizado);
            return ResponseEntity.ok(motoristaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }





}
