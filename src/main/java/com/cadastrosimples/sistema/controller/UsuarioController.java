package com.cadastrosimples.sistema.controller;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listarTodos();
    }

    // 🔥 novo endpoint
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Usuario>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<Usuario> usuarios = service.listarPorEmpresa(empresaId);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<Usuario> cadastrar(
            @RequestBody Usuario usuario,
            @PathVariable Long empresaId) {

        Usuario novoUsuario = service.cadastrar(usuario, empresaId);

        if (novoUsuario != null) {
            return ResponseEntity.ok(novoUsuario);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable long id,
            @RequestBody Usuario dadosAtualizados) {

        Usuario usuarioAtualizado = service.atualizar(id, dadosAtualizados);

        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}