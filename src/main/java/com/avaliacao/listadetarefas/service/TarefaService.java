package com.avaliacao.listadetarefas.service;

import com.avaliacao.listadetarefas.entity.Tarefa;
import com.avaliacao.listadetarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    
    @Autowired
    private TarefaRepository tarefaRepository;
    
    // LISTAR TODAS AS TAREFAS
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }
    
    // BUSCAR TAREFA POR ID
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }
    
    // CRIAR TAREFA 
    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }
    
    // ALTERAR TAREFA 
    public Tarefa alterar(Long id, Tarefa tarefaAtualizada) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        
        if (tarefaOptional.isPresent()) {
            Tarefa tarefaExistente = tarefaOptional.get();
            
            
            if (tarefaAtualizada.getNome() != null) {
                tarefaExistente.setNome(tarefaAtualizada.getNome());
            }
            if (tarefaAtualizada.getDescricao() != null) {
                tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
            }
            if (tarefaAtualizada.getStatus() != null) {
                tarefaExistente.setStatus(tarefaAtualizada.getStatus());
            }
            if (tarefaAtualizada.getObservacoes() != null) {
                tarefaExistente.setObservacoes(tarefaAtualizada.getObservacoes());
            }
            
            return tarefaRepository.save(tarefaExistente);
        } else {
            throw new RuntimeException("Tarefa não encontrada com id: " + id);
        }
    }
    
    // DELETAR TAREFA 
    public void deletar(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tarefa não encontrada com id: " + id);
        }
    }
}