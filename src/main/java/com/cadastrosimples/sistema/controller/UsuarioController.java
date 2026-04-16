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

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = service.cadastrar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable long id, @RequestBody Usuario dadosAtualizados) {
        Usuario usuarioAtualizado = service.atualizar(id, dadosAtualizados);

        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado);
        }

        return ResponseEntity.notFound().build();
    }
}
/*
Rota de ENDPOINTS
 */