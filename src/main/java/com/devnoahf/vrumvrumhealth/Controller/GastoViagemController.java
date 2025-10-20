package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.GastoViagemDTO;
import com.devnoahf.vrumvrumhealth.Service.GastoViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gasto-viagem")
public class GastoViagemController {

    @Autowired
    private GastoViagemService gastoViagemService;

    @GetMapping("/teste")
    public String teste() {
        return "teste gasto viagem";
    }

    @PostMapping
    public ResponseEntity<GastoViagemDTO> criar(@RequestBody GastoViagemDTO gastoDTO) {
        GastoViagemDTO novoGasto = gastoViagemService.cadastrarGastoViagem(gastoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGasto);
    }

    @GetMapping
    public ResponseEntity<List<GastoViagemDTO>> listar() {
        List<GastoViagemDTO> gastos = gastoViagemService.listarGastos();
        return ResponseEntity.ok(gastos);
    }


    @GetMapping("{id}")
    public ResponseEntity<GastoViagemDTO> buscarPorId(@PathVariable Long id) {
        GastoViagemDTO gastoDTO = gastoViagemService.buscarPorId(id);
        if (gastoDTO != null) {
            return ResponseEntity.ok(gastoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody GastoViagemDTO gastoDTO) {
        GastoViagemDTO gastoAtualizado = gastoViagemService.atualizarGasto(id, gastoDTO);
        if (gastoAtualizado != null) {
            return ResponseEntity.ok(gastoAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gasto de viagem não encontrado");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        GastoViagemDTO gastoDTO = gastoViagemService.buscarPorId(id);
        if (gastoDTO != null) {
            gastoViagemService.deletarGasto(id);
            return ResponseEntity.ok("Gasto de viagem deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gasto de viagem não encontrado");
        }
    }
}
