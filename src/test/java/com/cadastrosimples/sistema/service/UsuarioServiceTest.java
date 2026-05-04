package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.UsuarioRepository;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import com.cadastrosimples.sistema.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("Mateus");
        usuario.setEmail("mateus@email.com");
        usuario.setTelefone("71999999999");
    }

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        when(repository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void listarPorEmpresa_QuandoEmpresaNaoExiste_DeveLancarException() {
        when(empresaRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.listarPorEmpresa(1L);
        });

        verify(empresaRepository).existsById(1L);
        verify(repository, never()).findByEmpresaId(anyLong());
    }

    @Test
    void listarPorEmpresa_QuandoEmpresaExiste_DeveRetornarLista() {
        when(empresaRepository.existsById(1L)).thenReturn(true);
        when(repository.findByEmpresaId(1L)).thenReturn(Arrays.asList(usuario));

        List<Usuario> resultado = service.listarPorEmpresa(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(empresaRepository).existsById(1L);
        verify(repository).findByEmpresaId(1L);
    }

    @Test
    void cadastrar_QuandoEmpresaExiste_DeveSalvarUsuario() {
        Empresa empresa = mock(Empresa.class);

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.cadastrar(usuario, 1L);

        assertNotNull(resultado);
        verify(empresaRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void cadastrar_QuandoEmpresaNaoExiste_DeveLancarException() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.cadastrar(usuario, 1L);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void atualizar_QuandoUsuarioExiste_DeveAtualizarDados() {
        Usuario dadosAtualizados = new Usuario();
        dadosAtualizados.setNome("Novo Nome");
        dadosAtualizados.setEmail("novo@email.com");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.atualizar(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
        verify(repository).findById(1L);
        verify(repository).save(usuario);
    }

    @Test
    void atualizar_QuandoUsuarioNaoExiste_DeveLancarException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.atualizar(1L, new Usuario());
        });

        verify(repository, never()).save(any());
    }

    @Test
    void atualizar_QuandoEmpresaNaoExiste_DeveLancarException() {
        Usuario usuarioExistente = new Usuario();

        Usuario dadosAtualizados = new Usuario();

        Empresa empresaMock = mock(Empresa.class);
        when(empresaMock.getId()).thenReturn(99L);

        dadosAtualizados.setEmpresa(empresaMock);

        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(empresaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.atualizar(1L, dadosAtualizados);
        });

        verify(repository).findById(1L);
        verify(empresaRepository).findById(99L);
        verify(repository, never()).save(any());
    }

    @Test
    void remover_QuandoUsuarioExiste_DeveRemover() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean resultado = service.remover(1L);

        assertTrue(resultado);
        verify(repository).deleteById(1L);
    }

    @Test
    void remover_QuandoUsuarioNaoExiste_DeveLancarException() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.remover(1L);
        });

        verify(repository, never()).deleteById(anyLong());
    }
}