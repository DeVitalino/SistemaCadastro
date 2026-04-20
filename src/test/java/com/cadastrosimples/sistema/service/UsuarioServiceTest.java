package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");
    }

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        when(repository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuario.getNome(), resultado.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void cadastrar_DeveRetornarUsuarioSalvo() {
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.cadastrar(new Usuario());

        assertNotNull(resultado);
        assertEquals(usuario.getNome(), resultado.getNome());
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void atualizar_QuandoUsuarioExiste_DeveRetornarUsuarioAtualizado() {
        Usuario dadosAtualizados = new Usuario();
        dadosAtualizados.setNome("João Alterado");
        dadosAtualizados.setEmail("joao.novo@email.com");
        dadosAtualizados.setTelefone("11888888888");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.atualizar(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("João Alterado", resultado.getNome());
        assertEquals("joao.novo@email.com", resultado.getEmail());
        assertEquals("11888888888", resultado.getTelefone());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void atualizar_QuandoUsuarioNaoExiste_DeveRetornarNull() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Usuario resultado = service.atualizar(1L, new Usuario());

        assertNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void remover_QuandoUsuarioExiste_DeveRetornarTrue() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean resultado = service.remover(1L);

        assertTrue(resultado);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void remover_QuandoUsuarioNaoExiste_DeveRetornarFalse() {
        when(repository.existsById(1L)).thenReturn(false);

        boolean resultado = service.remover(1L);

        assertFalse(resultado);
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}
