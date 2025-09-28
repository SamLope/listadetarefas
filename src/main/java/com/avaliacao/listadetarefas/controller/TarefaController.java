package com.avaliacao.listadetarefas.controller;

import com.avaliacao.listadetarefas.entity.Tarefa;
import com.avaliacao.listadetarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {
    
    @Autowired
    private TarefaService tarefaService;
    
    // LISTAR TODAS AS TAREFAS
    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaService.listarTodas();
    }
    
    // BUSCAR TAREFA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaService.buscarPorId(id);
        return tarefa.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    // CRIAR TAREFA  
    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
        try {
            Tarefa novaTarefa = tarefaService.criar(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ALTERAR TAREFA 
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> alterar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            Tarefa tarefaAtualizada = tarefaService.alterar(id, tarefa);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETAR TAREFA 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}