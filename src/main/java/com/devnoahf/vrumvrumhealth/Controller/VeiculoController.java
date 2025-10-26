package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.VeiculoDTO;
import com.devnoahf.vrumvrumhealth.Service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarTodos() {
        List<VeiculoDTO> lista = veiculoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> buscarPorId(@PathVariable Long id) {
        VeiculoDTO dto = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO novo = veiculoService.salvar(veiculoDTO);
        return ResponseEntity.status(201).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizar(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO atualizado = veiculoService.atualizar(id, veiculoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
