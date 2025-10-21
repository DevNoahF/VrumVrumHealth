package com.devnoahf.vrumvrumhealth.Controller;

import com.devnoahf.vrumvrumhealth.DTO.AdmDTO;
import com.devnoahf.vrumvrumhealth.Service.AdmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm")
public class AdmController {

    private final AdmService admService;

    public AdmController(AdmService admService) {
        this.admService = admService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/teste")
    public String teste(){
        return "teste";
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        AdmDTO admDTO = admService.buscarPorId(id);
        if (id!= null){
            admService.deletarAdm(id);
            return ResponseEntity
                    .ok("adm deletado com sucesso!");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("adm não encontrado");
        }

    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AdmDTO admDTO){
        AdmDTO novoAdm = admService.cadastrarAdm(admDTO);
        return ResponseEntity
                .ok(admDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@RequestBody AdmDTO admDTO, @PathVariable Long id){
        AdmDTO admAtualizado = admService.atualizarAdm(admDTO, id);
        if (admAtualizado != null){
            return ResponseEntity
                    .ok(admAtualizado);
        } else {
            return ResponseEntity
                    .status(404)
                    .body("adm não encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<AdmDTO>> listar(){
        List<AdmDTO> admins = admService.listarAdmins();
        return ResponseEntity
                .ok(admins);
    }

    @GetMapping("{id}")
    public ResponseEntity<AdmDTO> buscarPorId(@PathVariable Long id){
        AdmDTO adm = admService.buscarPorId(id);
        if (adm != null){
            return ResponseEntity
                    .ok(adm);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    public ResponseEntity<AdmDTO> mudarSenha(@RequestParam String email, @RequestParam String novaSenha){
        admService.mudarSenha(email, novaSenha);
        return ResponseEntity
                .ok()
                .build();
    }


}
