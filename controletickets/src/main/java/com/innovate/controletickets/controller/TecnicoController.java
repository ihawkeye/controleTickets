package com.innovate.controletickets.controller;

import com.innovate.controletickets.model.Tecnico;
import com.innovate.controletickets.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @PostMapping
    public ResponseEntity<?> criarTecnico(@RequestParam String nome) {
        try {
            Tecnico novoTecnico = new Tecnico();
            novoTecnico.setNome(nome);

            tecnicoService.salvar(novoTecnico);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o técnico: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> listarTodos() {
        List<Tecnico> tecnicos = tecnicoService.listarTodos();
        return ResponseEntity.ok(tecnicos);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        Tecnico tecnico = tecnicoService.buscarPorNome(nome);
        if (tecnico != null) {
            return ResponseEntity.ok(tecnico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}