package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.DiarioBordoDTO;
import com.devnoahf.vrumvrumhealth.Mapper.DiarioBordoMapper;
import com.devnoahf.vrumvrumhealth.Model.DiarioBordo;
import com.devnoahf.vrumvrumhealth.Service.DiarioBordoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("diarioBordo")
@RequiredArgsConstructor
public class DiarioBordoController {

    private final DiarioBordoService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DiarioBordoDTO dto){
        DiarioBordoDTO diarioBordo = DiarioBordoMapper.toEntity(dto);
        DiarioBordo diarioBordoSalvo = service.salvar(diarioBordo);
        DiarioBordo diarioBordoDTO = DiarioBordoMapper.toDTO(diarioBordoSalvo);
        return ResponseEntity.status(201).body(diarioBordoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiarioBordo> update(@PathVariable Long id, @RequestBody DiarioBordoDTO dto){
        DiarioBordoDTO diarioBordoSalvo = DiarioBordoMapper.toEntity(dto);
        DiarioBordo diarioBordoAtualizado = service.update(id, diarioBordoSalvo);
        DiarioBordo diarioBordoDTO = DiarioBordoMapper.toDTO(diarioBordoAtualizado);
        return ResponseEntity.ok(diarioBordoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null){
            service.delete(id);
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).body("Diario de Bordo nao encontrado!");
        }
    }

    @GetMapping
    public ResponseEntity<List<DiarioBordo>> listAll(){
        List<DiarioBordo> diarioBordo = service.listAll().stream()
                .map(DiarioBordoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(diarioBordo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id){
        DiarioBordo diarioBordo = service.listById(id);
        if (id != null){
            DiarioBordo diarioBordoDTO = DiarioBordoMapper.toDTO(diarioBordo);
            return ResponseEntity.ok(diarioBordoDTO);
        } else {
            return ResponseEntity.status(404).body("Diario de Bordo nao encontrado!");
        }
    }


}
