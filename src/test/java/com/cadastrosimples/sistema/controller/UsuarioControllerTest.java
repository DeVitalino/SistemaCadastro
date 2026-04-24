package com.cadastrosimples.sistema.controller;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_DeveRetornarUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("João");

        Mockito.when(service.listarTodos()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    void listarPorEmpresa_DeveRetornarUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("João");

        Mockito.when(service.listarPorEmpresa(1L))
                .thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/usuarios/empresa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    void cadastrar_DeveRetornarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("João");

        Mockito.when(service.cadastrar(Mockito.any(), Mockito.eq(1L)))
                .thenReturn(usuario);

        mockMvc.perform(post("/usuarios/empresa/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void cadastrar_QuandoEmpresaNaoExiste_DeveRetornar404() throws Exception {
        Mockito.when(service.cadastrar(Mockito.any(), Mockito.eq(1L)))
                .thenReturn(null);

        mockMvc.perform(post("/usuarios/empresa/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Usuario())))
                .andExpect(status().isNotFound());
    }

    @Test
    void remover_DeveRetornarOk() throws Exception {
        Mockito.when(service.remover(1L)).thenReturn(true);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isOk());
    }
}