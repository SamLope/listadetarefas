package com.avaliacao.listadetarefas.controller;

import com.avaliacao.listadetarefas.entity.Tarefa;
import com.avaliacao.listadetarefas.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarefaService tarefaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarTodasTarefas() throws Exception {
        // Arrange
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        tarefa1.setNome("Tarefa 1");
        
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setNome("Tarefa 2");
        
        when(tarefaService.listarTodas()).thenReturn(Arrays.asList(tarefa1, tarefa2));

        // Act & Assert
        mockMvc.perform(get("/api/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Tarefa 1"))
                .andExpect(jsonPath("$[1].nome").value("Tarefa 2"));
    }

    @Test
    void deveBuscarTarefaPorId() throws Exception {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setNome("Tarefa Teste");
        
        when(tarefaService.buscarPorId(1L)).thenReturn(Optional.of(tarefa));

        // Act & Assert
        mockMvc.perform(get("/api/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Tarefa Teste"));
    }

    @Test
    void deveRetornarNotFoundParaTarefaInexistente() throws Exception {
        // Arrange
        when(tarefaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/tarefas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarTarefa() throws Exception {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Nova Tarefa");
        tarefa.setDescricao("Descrição da tarefa");
        
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(1L);
        tarefaSalva.setNome("Nova Tarefa");
        
        when(tarefaService.criar(any(Tarefa.class))).thenReturn(tarefaSalva);

        // Act & Assert
        mockMvc.perform(post("/api/tarefas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nova Tarefa"));
    }

    @Test
    void deveAlterarTarefa() throws Exception {
        // Arrange
        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setNome("Tarefa Atualizada");
        
        Tarefa tarefaRetorno = new Tarefa();
        tarefaRetorno.setId(1L);
        tarefaRetorno.setNome("Tarefa Atualizada");
        
        when(tarefaService.alterar(eq(1L), any(Tarefa.class))).thenReturn(tarefaRetorno);

        // Act & Assert
        mockMvc.perform(put("/api/tarefas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Tarefa Atualizada"));
    }

    @Test
    void deveDeletarTarefa() throws Exception {
        // Arrange
        doNothing().when(tarefaService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tarefas/1"))
                .andExpect(status().isNoContent());
        
        verify(tarefaService, times(1)).deletar(1L);
    }
}