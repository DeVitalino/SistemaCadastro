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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("12345678000100");

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
        verify(repository, times(1)).findAll();
    }

    @Test
    void cadastrar_QuandoEmpresaExiste_DeveSalvarUsuario() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.cadastrar(usuario, 1L);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(empresaRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void cadastrar_QuandoEmpresaNaoExiste_DeveLancarException() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.cadastrar(usuario, 1L);
        });

        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void atualizar_DeveAtualizarDados() {
        Usuario dados = new Usuario();
        dados.setNome("Novo Nome");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.atualizar(1L, dados);

        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
    }

    @Test
    void atualizar_QuandoNaoExiste_DeveLancarException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.atualizar(1L, new Usuario());
        });
    }

    @Test
    void remover_QuandoExiste_DeveRetornarTrue() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean resultado = service.remover(1L);

        assertTrue(resultado);
        verify(repository).deleteById(1L);
    }

    @Test
    void remover_QuandoNaoExiste_DeveLancarException() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.remover(1L);
        });

        verify(repository, never()).deleteById(anyLong());
    }
}