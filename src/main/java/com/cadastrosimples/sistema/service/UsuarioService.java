package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private Random gerador = new Random();

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario cadastrar(Usuario usuario) {
        int id = gerador.nextInt(900) + 100;
        usuario.setId(id);
        return repository.save(usuario);
    }

    public boolean remover(int id) {
        return repository.deleteById(id);
    }
}
