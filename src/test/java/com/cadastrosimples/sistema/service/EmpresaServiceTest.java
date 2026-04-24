package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository repository;

    @InjectMocks
    private EmpresaService service;

    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setNome("MAGTECH");
        empresa.setCnpj("12345678000100");
    }

    @Test
    void listarTodos_DeveRetornarListaDeEmpresas() {
        when(repository.findAll()).thenReturn(Arrays.asList(empresa));

        List<Empresa> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(empresa.getNome(), resultado.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void cadastrar_DeveRetornarEmpresaSalva() {
        when(repository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa resultado = service.cadastrar(new Empresa());

        assertNotNull(resultado);
        assertEquals(empresa.getNome(), resultado.getNome());
        verify(repository, times(1)).save(any(Empresa.class));
    }

    @Test
    void atualizar_QuandoEmpresaExiste_DeveRetornarEmpresaAtualizada() {
        Empresa dadosAtualizados = new Empresa();
        dadosAtualizados.setNome("Empresa Nova");
        dadosAtualizados.setCnpj("99999999000100");

        when(repository.findById(1L)).thenReturn(Optional.of(empresa));
        when(repository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa resultado = service.atualizar(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("Empresa Nova", resultado.getNome());
        assertEquals("99999999000100", resultado.getCnpj());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Empresa.class));
    }

    @Test
    void atualizar_QuandoEmpresaNaoExiste_DeveRetornarNull() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Empresa resultado = service.atualizar(1L, new Empresa());

        assertNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Empresa.class));
    }

    @Test
    void remover_QuandoEmpresaExiste_DeveRetornarTrue() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean resultado = service.remover(1L);

        assertTrue(resultado);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void remover_QuandoEmpresaNaoExiste_DeveRetornarFalse() {
        when(repository.existsById(1L)).thenReturn(false);

        boolean resultado = service.remover(1L);

        assertFalse(resultado);
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}