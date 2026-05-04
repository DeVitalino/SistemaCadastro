package com.cadastrosimples.sistema.controller;

import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.service.EmpresaService;
import com.cadastrosimples.sistema.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_DeveRetornarListaDeEmpresas() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa");
        empresa.setCnpj("12345678000100");

        Mockito.when(service.listarTodos()).thenReturn(Arrays.asList(empresa));

        mockMvc.perform(get("/empresas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Empresa")); // 🔧 corrigido
    }

    @Test
    void cadastrar_DeveRetornarEmpresaCriada() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa");
        empresa.setCnpj("12345678000100");

        Mockito.when(service.cadastrar(Mockito.any(Empresa.class))).thenReturn(empresa);

        mockMvc.perform(post("/empresas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa"));
    }

    @Test
    void atualizar_QuandoEmpresaExiste_DeveRetornarOk() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setNome("Atualizada");

        Mockito.when(service.atualizar(Mockito.eq(1L), Mockito.any(Empresa.class)))
                .thenReturn(empresa);

        mockMvc.perform(put("/empresas/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Atualizada"));
    }

    @Test
    void atualizar_QuandoEmpresaNaoExiste_DeveRetornar404() throws Exception {
        Mockito.when(service.atualizar(Mockito.eq(1L), Mockito.any(Empresa.class)))
                .thenThrow(new ResourceNotFoundException("Empresa não encontrada")); // 🔥 mudou

        mockMvc.perform(put("/empresas/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Empresa())))
                .andExpect(status().isNotFound());
    }

    @Test
    void remover_QuandoEmpresaExiste_DeveRetornarOk() throws Exception {
        Mockito.doNothing().when(service).remover(1L); // 🔥 mudou

        mockMvc.perform(delete("/empresas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void remover_QuandoEmpresaNaoExiste_DeveRetornar404() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Empresa não encontrada"))
                .when(service).remover(1L); // 🔥 mudou

        mockMvc.perform(delete("/empresas/1"))
                .andExpect(status().isNotFound());
    }
}