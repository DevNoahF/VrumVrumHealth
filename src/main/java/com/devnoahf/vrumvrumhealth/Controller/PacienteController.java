package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        PacienteDTO pacienteDTO = pacienteService.buscarPorIdPaciente(id);
        if (id!= null){
            pacienteService.deletarPaciente(id);
            return ResponseEntity
                    .ok("adm deletado com sucesso!");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("adm não encontrado");
        }

    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody PacienteDTO pacienteDTO){
        PacienteDTO paciente = pacienteService.cadastrarPaciente(pacienteDTO);
        return ResponseEntity
                .ok(pacienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody PacienteDTO pacienteDTO, @PathVariable Long id){
        PacienteDTO pacienteAtualizado = pacienteService.atualizarPaciente(pacienteDTO, id);
        if (pacienteAtualizado != null){
            return ResponseEntity
                    .ok(pacienteAtualizado);
        } else {
            return ResponseEntity
                    .status(404)
                    .body("adm não encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listar(){
        List<PacienteDTO> paciente = pacienteService.listarPaciente();
        return ResponseEntity
                .ok(paciente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id){
        PacienteDTO pacienteDTO = pacienteService.buscarPorIdPaciente(id);
        if (pacienteDTO != null){
            return ResponseEntity
                    .ok(pacienteDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    public ResponseEntity<PacienteDTO> mudarSenha(@RequestParam String email, @RequestParam String novaSenha){
        pacienteService.mudarSenhaPaciente( email, novaSenha);
        return ResponseEntity
                .ok()
                .build();
    }


}
