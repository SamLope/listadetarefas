package com.avaliacao.listadetarefas.service;

import com.avaliacao.listadetarefas.entity.Tarefa;
import com.avaliacao.listadetarefas.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveListarTodasTarefas() {
        // Arrange
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setNome("Tarefa 1");
        
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNome("Tarefa 2");
        
        List<Tarefa> tarefas = Arrays.asList(tarefa1, tarefa2);
        
        when(tarefaRepository.findAll()).thenReturn(tarefas);

        // Act
        List<Tarefa> resultado = tarefaService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorId() {
        // Arrange
        Long id = 1L;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setNome("Tarefa Teste");
        
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));

        // Act
        Optional<Tarefa> resultado = tarefaService.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Tarefa Teste", resultado.get().getNome());
        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    void deveCriarTarefa() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Nova Tarefa");
        tarefa.setDescricao("Descrição da tarefa");
        
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.criar(tarefa);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nova Tarefa", resultado.getNome());
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveAlterarTarefa() {
        // Arrange
        Long id = 1L;
        Tarefa tarefaExistente = new Tarefa();
        tarefaExistente.setId(id);
        tarefaExistente.setNome("Tarefa Antiga");
        
        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setNome("Tarefa Atualizada");
        tarefaAtualizada.setDescricao("Nova descrição");
        
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaExistente));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaExistente);

        // Act
        Tarefa resultado = tarefaService.alterar(id, tarefaAtualizada);

        // Assert
        assertNotNull(resultado);
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).save(tarefaExistente);
    }

    @Test
    void deveDeletarTarefa() {
        // Arrange
        Long id = 1L;
        when(tarefaRepository.existsById(id)).thenReturn(true);

        // Act
        tarefaService.deletar(id);

        // Assert
        verify(tarefaRepository, times(1)).existsById(id);
        verify(tarefaRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        // Arrange
        Long id = 99L;
        when(tarefaRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            tarefaService.deletar(id);
        });
        
        verify(tarefaRepository, times(1)).existsById(id);
        verify(tarefaRepository, never()).deleteById(id);
    }
}