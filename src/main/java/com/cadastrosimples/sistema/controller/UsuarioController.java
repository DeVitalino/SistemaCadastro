package com.cadastrosimples.sistema.controller;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Usuario>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<Usuario> cadastrar(
            @RequestBody Usuario usuario,
            @PathVariable Long empresaId) {

        Usuario novoUsuario = service.cadastrar(usuario, empresaId);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable long id,
            @RequestBody Usuario dadosAtualizados) {

        return ResponseEntity.ok(service.atualizar(id, dadosAtualizados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build(); // 204
    }
}